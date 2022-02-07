package com.leejohy.restfulwebservice.user;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
// @JsonIgnoreProperties(value = {"password", "ssn"})
// @JsonFilter("UserInfo")
@NoArgsConstructor // 상속하기 위해선 디폴트 생성자 구현 필요
public class User {

    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;

    @Past
    private Date joinDate;

    private String password;
    private String ssn;
}
