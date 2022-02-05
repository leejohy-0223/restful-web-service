package com.leejohy.restfulwebservice.user;

import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin") // Controller에 공용 prefix를 지정
public class AdminUserController {
    private UserDaoService service; // spring을 통한 DI로 주입받을 것이다. (bean 으로 주입받는다.)

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users); // 매개 변수만 변경해주면 된다.
        mapping.setFilters(filters);

        return mapping;
    }

    // /users/1 -> 기본은 문자 형태로 받아진다. int로 선언하면 문자가 int로 자동으로 converting 된다.
    // retrieve : 검색하다
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        /**
         * filter를 통해 어노테이션 대신 코드레벨에서 직관적인 제어가 가능하다.
         * filterOutAllException으로 지정된 프로퍼티만 통과하도록 한다.
         */
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // 앞서 만든 필터를 FilterProvider에게 전달한다.
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // JacksonValue를 만들고, 여기에 FilterProvider를 세팅한다.
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }
}
