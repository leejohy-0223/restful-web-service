package com.leejohy.restfulwebservice.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource; // 스프링 프레임워크에 등록되어 있는 빈 중, 해당하는 빈을 주입해준다.

    // @Autowired
    // private ReloadableResourceBundleMessageSource reloadable;

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

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(
        @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", new String[] {"leejohy", "Lucid"}, locale);
    }

    // @GetMapping("/hello-world-reloadableMessageSource")
    // public String helloWorldReloadable(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    //     return reloadable.getMessage("greeting.message", null, locale);
    // }
}
