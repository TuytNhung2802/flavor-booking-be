package com.flavorbooking.models;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private LocalDateTime accessExpirationDate;
    private LocalDateTime refreshExpirationDate;
    private boolean isRevoked;
    private boolean isExpired;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}