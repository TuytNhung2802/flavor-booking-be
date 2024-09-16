package com.flavorbooking.services.impl.auth;

import com.flavorbooking.dtos.auth.TokenDTO;
import com.flavorbooking.models.Account;
import com.flavorbooking.models.Token;
import com.flavorbooking.repositories.User.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    // giới hạn số lượng token - mục đích tránh đăng nhập nhiều nơi.
    private static final int MAX_TOKENS = 1;

    private final TokenRepository tokenRepository;
    private final ModelMapper mapper;

    public void addToken(TokenDTO tokenDTO, Account account) {
        List<Token> accountTokens = tokenRepository.findByAccount(account);

        int tokenCount = accountTokens.size();

        // if access token more than Max_token, delete first token in list then insert next token.
        if (tokenCount >= MAX_TOKENS) {
            Token tokenToDelete = accountTokens.get(0);
            tokenRepository.delete(tokenToDelete);
        }

        Token newToken = Token.builder()
                .account(account)
                .accessToken(tokenDTO.getAccess())
                .refreshToken(tokenDTO.getRefresh())
                .isRevoked(false)
                .isExpired(false)
                .tokenType("Bearer")
                .accessExpirationDate(tokenDTO.getExpirationAccessToken())
                .refreshExpirationDate(tokenDTO.getExpirationRefreshToken())
                .build();

        tokenRepository.save(newToken);
    }
}
