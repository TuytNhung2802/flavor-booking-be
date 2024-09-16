package com.flavorbooking.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flavorbooking.dtos.response.LoginErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenEntrypoint implements AuthenticationEntryPoint {

     // handle 403 HTTP to 401 HTTP  when user dont have permission or request "empty" access token
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        LoginErrorResponse error = new LoginErrorResponse("Invalid username or password");
        mapper.writeValue(responseStream, error);
    }
}
