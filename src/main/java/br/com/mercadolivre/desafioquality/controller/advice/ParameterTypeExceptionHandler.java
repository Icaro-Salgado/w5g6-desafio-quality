package br.com.mercadolivre.desafioquality.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ParameterTypeExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleTypeConversionException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique se os valores enviados correspondem com o esperado");
    }
}
