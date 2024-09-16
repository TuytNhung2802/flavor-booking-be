package com.flavorbooking.filters.jwt;

import com.flavorbooking.exceptions.TokenException;
import com.flavorbooking.models.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // Inject Account ( because implement UserDetails )
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    /**
     * When client send request, it will go here first
     *
     * @param request:     request a header, then we can get token from header
     * @param filterChain: go to next security filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // request without access token
        if (isByPassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // request need accesstoken
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new TokenException("Invalid access token!");
        }

        final String token = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(token);
        System.out.println(token);

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            Account userDetails = (Account) userDetailsService.loadUserByUsername(username);

            // validate token.
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // ( next step sang WebSecurityConfig class) - enable bypass
        filterChain.doFilter(request, response);
    }


    /**
     * Kiểm tra api nếu chứa trong list api này thì
     * sẽ không kiểm tra acccess token
     *
     * @param request: url api
     * @return : true nếu chứa, false nếu không chứa trong list api
     */
    private boolean isByPassToken(HttpServletRequest request) {
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of("/api/restaurant", "GET"),
                Pair.of("/api/restaurant-hot", "GET"),
                Pair.of("/api/detail-restaurant", "GET"),
                Pair.of("/api/detail-dish", "GET"),
                Pair.of("api/get-table", "GET"),
                Pair.of("/api/dish-featured", "GET"),
                Pair.of("/api/dish-by-category", "GET"),
                Pair.of("/api/category", "GET"),
                Pair.of("/api/dishes-of-restaurant", "GET"),
                Pair.of("/api/dish", "GET"),
                Pair.of("/api/filter-product", "GET"),
                Pair.of("/api/search-restaurant", "GET"),
                Pair.of("/api/search-dish", "GET"),
                Pair.of("/api/filter-product", "POST"),
                Pair.of("/api/search-ai", "GET"),
                Pair.of("/auth/api/", "POST"),
                Pair.of("/api/register-restaurant/", "POST")
        );
        for (Pair<String, String> bypassToken : byPassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
