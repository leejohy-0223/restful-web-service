package com.leejohy.restfulwebservice.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timeStamp; // 예외 발생 시간
    private String message; // 예외 메시지 : exception.getMessage()를 통해 예외 메시지를 저장할 예정.
    private String details; // 싱세 내용 : request.getDescription(false)를 통해 client정보(remoteArr, 세션, user) 제외한 오직 requestUri만 얻을 수 있다.
}
