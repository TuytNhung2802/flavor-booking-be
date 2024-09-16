package com.flavorbooking.exceptions;

import com.flavorbooking.responses.ErrorResponse;
import com.flavorbooking.responses.ResourceResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Những lỗi chung chung thì sẽ handle ở đây
     * @return message, status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Lỗi liên quan đến token: access, refresh, reset token
     * @return HTTP 401
     */
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException(TokenException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Khi đăng ký thất bại hoặc người dùng request không thoả mãn validate
     * @return HTTP 400
     */
    @ExceptionHandler(com.flavorbooking.exceptions.BadRequestException.class)
    public ResponseEntity<ResourceResponse<Object>> handleExpiredTokenException(com.flavorbooking.exceptions.BadRequestException ex, WebRequest webRequest) {
        ResourceResponse<Object> response = ResourceResponse.builder()
                .success(false)
                .data(null)
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle authorization, khi người dùng đăng nhập nhưng không đủ quyền để gọi api
     * @param ex
     * @param webRequest
     * @return 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }










}
