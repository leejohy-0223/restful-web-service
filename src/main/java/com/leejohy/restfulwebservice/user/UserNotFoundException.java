package com.leejohy.restfulwebservice.user;

// 2XX -> OK
// 4XX -> Client Error
// 5XX -> Server Error

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
