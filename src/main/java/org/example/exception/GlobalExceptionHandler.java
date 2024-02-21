package org.example.exception;

import graphql.GraphQLError;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.exception.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @GraphQlExceptionHandler({EntityAlreadyExistsException.class,
            EntityNotFoundException.class,
            DataIntegrityViolationException.class})
    public GraphQLError handleGraphQl(RuntimeException ex) {
        log.error(ex.getMessage());

        return GraphQLError.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
    }
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Object> handleRest(RuntimeException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
