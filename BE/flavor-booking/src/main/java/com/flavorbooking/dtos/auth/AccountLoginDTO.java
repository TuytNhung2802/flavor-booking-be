package com.flavorbooking.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccountLoginDTO {
    @JsonProperty("email")
    private String usernameOrEmail;
    private String password;
}
