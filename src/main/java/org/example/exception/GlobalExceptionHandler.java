package org.example.exception;

import graphql.GraphQLError;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.DTO.ExceptionResponse;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @GraphQlExceptionHandler({EntityAlreadyExistsException.class,
            EntityNotFoundException.class,
            UserNotFoundException.class,
            UserAlreadyExistsException.class})
    public GraphQLError handleGraphQl(RuntimeException ex) {
        log.error(ex.getMessage());

        return GraphQLError.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage())
                .build();
    }
//
//    @GraphQlExceptionHandler
//    public GraphQLError handle(EntityNotFoundException ex) {
//        log.error(ex.getMessage());
//        return GraphQLError.newError()
//                .errorType(ErrorType.BAD_REQUEST)
//                .message(ex.getMessage())
//                .build();
//    }
//
//    @GraphQlExceptionHandler
//    public GraphQLError handle(UserNotFoundException ex) {
//        log.error(ex.getMessage());
//        return GraphQLError.newError()
//                .errorType(ErrorType.BAD_REQUEST)
//                .message(ex.getMessage())
//                .build();
//    }
//
//    @GraphQlExceptionHandler
//    public GraphQLError handle(UserAlreadyExistsException ex) {
//        log.error(ex.getMessage());
//        return GraphQLError.newError()
//                .errorType(ErrorType.BAD_REQUEST)
//                .message(ex.getMessage())
//                .build();
//    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<Object> handleRest(RuntimeException ex){
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(LocalDateTime.now(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
