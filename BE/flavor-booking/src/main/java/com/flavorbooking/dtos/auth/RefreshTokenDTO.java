package com.flavorbooking.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDTO {
    @NotBlank(message = "Refresh token cannot be empty")
    @JsonProperty("refresh_token")
    private String refreshToken;
}