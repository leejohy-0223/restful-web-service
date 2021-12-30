package com.leejohy.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET 방식 메서드 사용
    // uri : /hello-world(호출하는 end point)
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("hello world");
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable(value = "name") String anotherName) {
        // String format을 통해 매개 변수를 받아서 Stirng을 만든다.
        return new HelloWorldBean(String.format("hello world, %s", anotherName));
    }

    @PostMapping("/hello-world-bean/path-variable")
    public HelloWorldBean helloBean() {
        return new HelloWorldBean("make String");
    }
}
