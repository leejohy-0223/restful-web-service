package com.leejohy.restfulwebservice;

import java.util.Locale;

import org.aspectj.bridge.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class RestfulWebServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestfulWebServiceApplication.class, args);
    }

    /**
     * 여기에서 빈을 등록하면, 다른 클래스들도 사용할 수 있게 된다.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver(); // 세션을 통해 LocaleResolver를 가져온다.
        localeResolver.setDefaultLocale(Locale.KOREA); // 기본 KOREA로 지정한다.
        return localeResolver;
    }

    /**
     * reloadable bean 추가 등록
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3);

        return messageSource;
    }
}
