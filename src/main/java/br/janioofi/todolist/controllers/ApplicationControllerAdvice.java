package br.janioofi.todolist.controllers;

import br.janioofi.todolist.domain.exceptions.ResourceNotFoundException;
import br.janioofi.todolist.domain.exceptions.ValidationErrors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(ResourceNotFoundException ex){
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors validationErrors(ConstraintViolationException ex, HttpServletRequest request){
        ValidationErrors field = new ValidationErrors();
        field.setPath(request.getRequestURI());
        field.setStatus(HttpStatus.BAD_REQUEST.value());
        field.setError("Validation Error");
        for(var x : ex.getConstraintViolations()){
            field.addErrors(x.getPropertyPath().toString(), x.getMessage());
        }
        log.error(field.toString());
        return field;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors validationErrors(DataIntegrityViolationException ex, HttpServletRequest request){
        ValidationErrors field = new ValidationErrors();
        field.setPath(request.getRequestURI());
        field.setStatus(HttpStatus.BAD_REQUEST.value());
        field.setError("Validation Error");
        field.addErrors("Data Integrity Violation", ex.getMessage());
        log.error(field.toString());
        return field;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors validationErrors(HttpMessageNotReadableException ex, HttpServletRequest request){
        ValidationErrors field = new ValidationErrors();
        field.setPath(request.getRequestURI());
        field.setStatus(HttpStatus.BAD_REQUEST.value());
        field.setError("Validation Error");
        field.addErrors("Data Integrity Violation", ex.getMessage());
        log.error(field.toString());
        return field;
    }
}