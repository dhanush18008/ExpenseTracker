package com.expenses.ExpenseTracker.Validation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ArgumentexceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String,String> handle(){
        Map<String,String> error=new HashMap<>();
        error.put("ResponseCode","01");
        error.put("ResponseMessage","NO SUCH CATEGORY EXISTS");
        return error;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String,String> handle1(){
        Map<String,String> error=new HashMap<>();
        error.put("ResponseCode","01");
        error.put("ResponseMessage","INVALID ID");
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String,String> handle2(MethodArgumentNotValidException ex){
        Map<String,String> error=new HashMap<>();
        error.put("ResponseCode","01");
        error.put("ResponseMessage","NUMBER DOESNOT EXISTS");
        ex.getBindingResult().getFieldErrors().forEach(err->{error.put(err.getField(),err.getDefaultMessage());});
        return error;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,String> handle3(){
        Map<String,String> error=new HashMap<>();
        error.put("ResponseCode","01");
        error.put("ResponseMessage","YOU SHOULD NOT SPEND ABOVE THE BUDGET");
        return error;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Map<String,String> handle4(){
        Map<String,String> error=new HashMap<>();
        error.put("ResponseCode","01");
        error.put("ResponseMessage","YOU CANT SPEND ABOVE THE BUDGET");
        return error;
    }
}
