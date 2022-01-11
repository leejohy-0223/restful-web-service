package com.leejohy.restfulwebservice.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leejohy.restfulwebservice.user.UserNotFoundException;

@RestController
@ControllerAdvice // 모든 컨트롤러 실행 시, 이 핸들러가 실행된다. 에러 발생 시 여기에서의 메서드가 실행될 수 있다.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // 모든 예외에 대해 처리
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
            new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class) // custom 예외를 handler 하도록 적용
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
            new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override // 어노테이션 추가를 통해, 메서드 명에 대한 검증을 컴파일 시점에 알아챌 수 있다.
    /**
     * ex : 발생한 exception 객체
     * header : request Header
     * status : http status
     * request : request
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
        //     ex.getMessage(), ex.getBindingResult().toString());

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
            "validation Failed", ex.getBindingResult().toString()); // time, message, details 순서

        // return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
