package com.example.bisneslogic.controllers;

import com.example.bisneslogic.common.*;
import com.example.bisneslogic.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

//    private final Map<String, ErrorCode> errorsMap;
    private final Map<String, HttpStatus> statusesMap;

    public ExceptionController() {

        this.statusesMap = new HashMap<>();

        this.statusesMap.put(CustomExeption.class.getName(), HttpStatus.BAD_REQUEST);
        this.statusesMap.put(ProductBadRequestException.class.getName(), HttpStatus.BAD_REQUEST);
        this.statusesMap.put(ProductNotFoundException.class.getName(), HttpStatus.NOT_FOUND);
        this.statusesMap.put(CartNotFoundException.class.getName(), HttpStatus.NOT_FOUND);
        this.statusesMap.put(DeliveryNotFoundException.class.getName(), HttpStatus.NOT_FOUND);
        this.statusesMap.put(OrderNotFoundException.class.getName(), HttpStatus.NOT_FOUND);
        this.statusesMap.put(DateNotValidException.class.getName(), HttpStatus.BAD_REQUEST);



    }

//    protected Object handleValidationError(Throwable throwable) {
//        ValidationException validationException = (ValidationException) throwable;
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ValidationErrorDTO.builder()
//                                .error(ErrorCode.VALIDATION_ERROR.name())
//                                .message(validationException.getFieldsErrors())
//                                .build()
//                );
//    }

//    protected Object handleConstraintViolationException(Throwable throwable) {
//        ConstraintViolationException validationError = (ConstraintViolationException) throwable;
//        Map<String, String> validationErrors = new HashMap<>();
//        validationError.getConstraintViolations().forEach(
//                c -> validationErrors.put(c.getPropertyPath().toString(), c.getMessage()));
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ValidationErrorDTO.builder()
//                                .error(ErrorCode.VALIDATION_ERROR.name())
//                                .message(validationErrors)
//                                .build()
//                );
//    }

    protected Object handleDefaultError(Throwable throwable, String errorName) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;


        if (this.statusesMap.containsKey(errorName)) statusCode = this.statusesMap.get(errorName);

        return ResponseEntity
                .status(statusCode)
                .body(
                        new ErrorDto(throwable.getMessage(), throwable.getClass().getName())
                );
    }


    protected Object handleCauseException(Throwable throwable)
            throws IOException {
        Exception causedException = (Exception) throwable;
        if (causedException.getCause() != null) return handleException(causedException.getCause());
        else return handleDefaultError(throwable, throwable.getClass().getName());
    }

//    protected Object handleJsonException(Throwable throwable) {
//        throwable.printStackTrace();
//        ErrorCode code = ErrorCode.JSON_SYNTAX_ERROR;
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ErrorDTO.builder()
//                                .error(code.name())
//                                .message(throwable.getMessage())
//                                .build()
//                );
//    }

//    protected Object handleDataFormatException(Throwable throwable) {
//        throwable.printStackTrace();
//        ErrorCode code = ErrorCode.INCORRECT_DATA_FORMAT;
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(
//                        ErrorDTO.builder()
//                                .error(code.name())
//                                .message(throwable.getMessage())
//                                .build()
//                );
//    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    protected Object handleException(Throwable throwable) throws IOException {
        String errorName = throwable.getClass().getName();
        switch (errorName) {
//            case "guldilin.errors.ValidationException":
//                return handleValidationError(throwable);
//            case "javax.validation.ConstraintViolationException":
//                return handleConstraintViolationException(throwable);
//            case "javax.persistence.PersistenceException":
//                return handlePersistenceException(throwable);
//            case "java.lang.IllegalArgumentException":
//            case "org.springframework.http.converter.HttpMessageNotReadableException":
//                return handleCauseException(throwable);
//            case "com.fasterxml.jackson.core.io.JsonEOFException":
//            case "com.fasterxml.jackson.core.JsonParseException":
//                return handleJsonException(throwable);
//            case "com.fasterxml.jackson.databind.exc.InvalidFormatException":
//                return handleDataFormatException(throwable);
            default:
                return handleDefaultError(throwable, errorName);
        }
    }
}
