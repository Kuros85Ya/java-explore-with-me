package ru.practicum.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationException(final ValidationException e) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Incorrectly made request.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFoundException(final NoSuchElementException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND, "The required object was not found.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFoundExceptionOther(final EmptyResultDataAccessException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND, "The required object was not found.", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleUnsupportedOperation(final DataIntegrityViolationException e) {
        return new ErrorResponseDto(HttpStatus.CONFLICT, "The required object was not found.", e.getMessage(), LocalDateTime.now());
    }
}