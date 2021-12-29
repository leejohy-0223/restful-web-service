package com.leejohy.restfulwebservice.user;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
// @AllArgsConstructor
public class UserController {
    private UserDaoService service; // spring을 통한 DI로 주입받을 것이다. (bean 으로 주입받는다.)

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // /users/1 -> 기본은 문자 형태로 받아진다. int로 선언하면 문자가 int로 자동으로 converting 된다.
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @PostMapping("/users") // 복수형으로 하는걸 권장. 단일 객체를 위한 uri가 아니라 users라는 데이터 리소스에 새로운 목록을 추가하기 위함이므로.
    public ResponseEntity<User> createUser(@RequestBody User user) { // object 형태의 데이터를 받기 위해서는, @RequestBody를 선언해야 한다.
        User savedUser = service.save(user);

        /*
         * ServletUriComponentsBuilder : 사용자 요청에 따른 작업을 처리한 후, 결과 값을 토대로 관련 URI를 생성해주는 역할을 수행한다.
         * fromCurrentRequest : 현재 요청되어진 request uri를 사용한다.
         * path() : 요청된 uri에 path를 추가한다.
         * buildAndExpand : 위의 중괄호에 들어갈 내용을 이 부분에서 지정한다.
         * ResponseEntity.created().build() : 응답 코드를 지정하고, Entity 를 반환한다.
         */

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")// 이 부분에는 buildAndExpand를 통해 얻은 값이 들어오게된다.
            .buildAndExpand(savedUser.getId())
            .toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("myResponseHeader", "customValue");
        return ResponseEntity.created(location).build(); // 빌더 패턴을 사용한 ResponseEntity
        // return new ResponseEntity<>(savedUser, responseHeaders, HttpStatus.CREATED);
    }
}
