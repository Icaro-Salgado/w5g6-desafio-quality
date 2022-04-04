package br.com.mercadolivre.desafioquality.controller.advice;

import br.com.mercadolivre.desafioquality.dto.error.ErrorDto;
import br.com.mercadolivre.desafioquality.exceptions.DbEntryAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DbEntryAlreadyExistsHandler {

    @ExceptionHandler(DbEntryAlreadyExists.class)
    protected ResponseEntity<Object> handleDbEntryAlreadyExists(DbEntryAlreadyExists ex) {

        String bodyOfResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(bodyOfResponse);
    }
}
