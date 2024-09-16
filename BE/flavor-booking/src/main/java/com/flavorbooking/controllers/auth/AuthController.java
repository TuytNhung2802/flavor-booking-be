package com.flavorbooking.controllers.auth;


import com.flavorbooking.dtos.auth.AccountLoginDTO;
import com.flavorbooking.dtos.auth.AccountRegisterDTO;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.responses.auth.AccountResponse;
import com.flavorbooking.services.impl.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResourceResponse<AccountResponse>> registerUser(
            @RequestBody AccountRegisterDTO request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResourceResponse<AccountResponse>> login(@RequestBody AccountLoginDTO request) {
        ResourceResponse<AccountResponse> response = authService
                .login(request.getUsernameOrEmail(), request.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/register/restaurant")
    public ResponseEntity<ResourceResponse<AccountResponse>> registerRestaurant(
            @RequestBody AccountRegisterDTO request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

}
