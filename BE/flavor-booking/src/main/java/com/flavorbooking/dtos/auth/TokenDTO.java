package com.flavorbooking.dtos.auth;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String access;
    private String refresh;
    private LocalDateTime expirationAccessToken;
    private LocalDateTime expirationRefreshToken;
}