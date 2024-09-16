package com.flavorbooking.services.impl.auth;

import com.flavorbooking.constant.SystemConstant;
import com.flavorbooking.dtos.auth.AccountRegisterDTO;
import com.flavorbooking.dtos.auth.TokenDTO;
import com.flavorbooking.exceptions.BadRequestException;
import com.flavorbooking.filters.jwt.JwtUtil;
import com.flavorbooking.models.Account;
import com.flavorbooking.models.Restaurant;
import com.flavorbooking.models.Role;
import com.flavorbooking.models.Token;
import com.flavorbooking.repositories.Restaurant.RestaurantRepository;
import com.flavorbooking.repositories.User.AccountRepository;
import com.flavorbooking.repositories.User.RoleRepository;
import com.flavorbooking.repositories.User.TokenRepository;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.responses.auth.AccountResponse;
import com.flavorbooking.responses.auth.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService  implements LogoutHandler {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final RestaurantRepository restaurantRepository;

    public ResourceResponse<AccountResponse> register(AccountRegisterDTO request) {
        String username = request.getUsername();
        String email = request.getEmail();

        if (!isExistUsernameOrEmail(username, email)) {
            // default is role user
            Role role;

            // else role Restaurant
            if(!request.isRestaurant()) {
                role = roleRepository.findByCode("ROLE_USER");
            } else {
                role = roleRepository.findByCode("ROLE_RESTAURANT");
            }


            Account newAccount = Account.builder()
                    .username(username)
                    .fullName(username)
                    .dateJoined(LocalDateTime.now())
                    .role(role)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(email)
                    .image("https://firebasestorage.googleapis.com/v0/b/intern-f77cc.appspot.com/o/defaultAvatar.png?alt=media") // phần xử lí với file làm sau
                    .provider("Email") // phần Oauth 2.0 làm sau
                    .isActive(true)
                    .build();

            accountRepository.save(newAccount);
        }

        Account exsitingAccount = accountRepository.findByUsername(username).get();
        TokenResponse tokenResponse = genToken(exsitingAccount);

        // convert entity obj to response obj.
        AccountResponse accountResponse = modelMapper.map(exsitingAccount, AccountResponse.class);
        accountResponse.setAvatar(exsitingAccount.getImage());
        accountResponse.setToken(tokenResponse);

        return ResourceResponse.<AccountResponse>builder()
                .success(true)
                .message("Đăng ký thành công")
                .data(accountResponse)
                .build();
    }

    private TokenResponse genToken(Account account) {
        // generate token.
        TokenDTO tokenDTO = jwtUtil.generateToken(account);

        // save token into database
        tokenService.addToken(tokenDTO, account);

        return new TokenResponse(tokenDTO.getRefresh(), tokenDTO.getAccess());

    }

    private boolean isExistUsernameOrEmail(String username, String email) {
        boolean isExistUsername = accountRepository.existsByUsername(username);
        boolean isExistEmail = accountRepository.existsByEmail(email);

        if (isExistUsername) {
            throw new BadRequestException("Username " + SystemConstant.EXSITS_IN_DB);
        }

        if (isExistEmail) {
            throw new BadRequestException("Email" + SystemConstant.EXSITS_IN_DB);
        }

        return false;
    }

    public ResourceResponse<AccountResponse> login(String usernameOrEmail, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        // invalid username
        if (optionalAccount.isEmpty()) {
            throw new BadRequestException("Tài khoản không tồn tại trong hệ thống");
        }

        Account existingAccount = optionalAccount.get();

        // check password
        if (!passwordEncoder.matches(password, existingAccount.getPassword())) {
            throw new BadRequestException("Sai mật khẩu!");
        }

        if (!existingAccount.isActive()) {
            throw new BadRequestException("Tài khoản đã bị khoá");
        }

        // response
        AccountResponse accountResponse = modelMapper.map(existingAccount, AccountResponse.class);


        Restaurant existingRestaurant = restaurantRepository.findByAccount_Id(existingAccount.getId());
        if(existingRestaurant != null) {
            if(existingRestaurant.getIsActive() == true) {
                accountResponse.setRestaurantId(existingRestaurant.getId());
            }
        }


        TokenResponse tokenResponse = genToken(existingAccount);

        // convert entity obj to response obj.

        accountResponse.setAvatar(existingAccount.getImage());
        accountResponse.setToken(tokenResponse);
        accountResponse.setRoleCode(existingAccount.getRole().getCode());

        return ResourceResponse.<AccountResponse>builder()
                .success(true)
                .message("Đăng nhập thành công")
                .data(accountResponse)
                .build();
    }


    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String accessToken = authHeader.substring(7);
        Token storedToken = tokenRepository.findByAccessToken(accessToken);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}
