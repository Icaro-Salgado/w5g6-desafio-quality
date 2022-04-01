package br.com.mercadolivre.desafioquality.controller.advice;

import br.com.mercadolivre.desafioquality.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ParameterTypeExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorDto> handleTypeConversionException(){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorDto("Erro durante a conversão dos parâmetros", "Verifique se os valores enviados correspondem com o esperado")
        );
    }
}
