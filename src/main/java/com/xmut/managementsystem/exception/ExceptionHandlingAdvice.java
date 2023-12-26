package com.xmut.managementsystem.exception;

import com.xmut.managementsystem.entity.Result;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(999999)
public class ExceptionHandlingAdvice {

    @ExceptionHandler(TokenAuthExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> HandleTokenAuthExpiredException(TokenAuthExpiredException ex){
        System.out.println("Handling TokenAuthExpiredException");
        Result<String> result = new Result<>();
        System.out.println(ex.getMessage());
        result.setMessage(ex.getMessage());
        result.setData(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return result;
    }
}
