package br.com.mercadolivre.desafioquality.controller.advice;

import br.com.mercadolivre.desafioquality.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<ErrorDto>> handleValidationError(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        List<ErrorDto> errorsDTO = errors
                .stream()
                .map(err -> {
                    ErrorDto error = new ErrorDto();
                    error.setName("valor inválido para o campo");
                    error.setMessage(err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(errorsDTO, HttpStatus.BAD_REQUEST);
    }
}
