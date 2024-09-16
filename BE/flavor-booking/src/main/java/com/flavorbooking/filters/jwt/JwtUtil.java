package com.flavorbooking.filters.jwt;

import com.flavorbooking.dtos.auth.TokenDTO;
import com.flavorbooking.exceptions.TokenException;
import com.flavorbooking.models.Account;
import com.flavorbooking.models.Token;
import com.flavorbooking.repositories.User.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final TokenRepository tokenRepository;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.exp-access-token}")
    private long expirationAccessToken;

    @Value("${jwt.exp-refresh-token}")
    private long expirationRefreshToken;

    // generate token
    public TokenDTO generateToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", account.getUsername());

        LocalDateTime accessTokenExp = LocalDateTime.now().plusSeconds(expirationAccessToken);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(account.getUsername())
                // parse LocalDateTime to Date.
                .setExpiration(Date.from(accessTokenExp.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact(); // build

        LocalDateTime refreshTokenExp = LocalDateTime.now().plusSeconds(expirationRefreshToken);
        String refreshToken = UUID.randomUUID().toString();

        return TokenDTO.builder()
                .access(accessToken)
                .expirationAccessToken(accessTokenExp)
                .refresh(refreshToken)
                .expirationRefreshToken(refreshTokenExp)
                .build();
    }

    private Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token) // token hết hạn nó sẽ chết ở đây.
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new TokenException("Expired access token");
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            Token existingToken = tokenRepository.findByAccessToken(token);
            if (existingToken == null || existingToken.isRevoked() || !userDetails.isEnabled()) {
                return false;
            }
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new TokenException("Unsupported JWT token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new TokenException("Jwt claims string is null or empty");
        } catch (MalformedJwtException malformedJwtException) {
            throw new TokenException("Invalid JWT Token");
        }
    }

    // check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

}
