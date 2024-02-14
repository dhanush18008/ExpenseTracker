package com.expenses.ExpenseTracker.Validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
@ResponseStatus(HttpStatus.NOT_FOUND)
@ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handle(MethodArgumentNotValidException ex){
    Map<String,String> error=new HashMap<>();
    error.put("ResponseCode","01");
    ex.getBindingResult().getFieldErrors().forEach(err->{error.put("ResponseMessage",err.getDefaultMessage());});
    return error;
}
}