## 3. RESTful Service 기능 확장

<br>

### Section 3 수업 소개
- Validation : 새로운 사용자 추가를 위한 클라이언트 입력 값에 대해 오류가 있는지 체크
- Internationalizaition : 서비스에서의 다국어 처리를 위한 라이브러리 사용
- XML format으로 반환하기 : 지금까지는 json 사용 > xml로 변환하는 방법에 대해 알아보기
- Filtering : User 클래스에 정의된 정보 중, 클라이언트에 보여질 정보 / 숨겨질 정보를 필터링
- Version 관리 : 개발한 어플리케이션을 버전별로 구분해서 서비스 할 수 있는 버전 관리 기능

<br>

### 유효성 체크를 위한 Validation API 사용 
- JDK, Hibernate에 포함된 API를 사용할 수 있다.
- User의 name, joinDate에 유효성 체크를 진행해보자.

````java
public class User {
    private Integer id;

    @Size(min=2)
    private String name;

    @Past
    private Date joinDate;
}
````

````java
    @PostMapping("/users") 
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) { 
        User savedUser = service.save(user);
    // ...     
````
- 우선 pom.xml에 `spring-boot-starter-validation`를 추가한다.
- name에는 2이상의 길이 제한을 두었다.
- joinDate에는 과거데이터만 올 수 있다는 제약 조건을 두었다.
- Postman을 통해 name이 2이상이 아닐 경우, 4xx로 응답하도록 구현하였다. controller의 User 앞에 @Valid를 넣으면 된다.

<br>

이번에는 body에 적절한 메시지를 출력하도록 해보자. ``ResponseEntityExceptionHandler``내의 아래 메서드를 재정의 한다.
재 정의를 ``CustomizedResponseEntityExceptionHandler``에 구현하면 된다.

````java
    // 재정의 전 
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
    }
    
    // 재정의 후
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
        "validation Failed", ex.getBindingResult().toString()); // time, message, details 순서

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
````
- 즉, 우리의 방식대로 ``ExceptionResponse``를 반환하도록 구현한다.
- `ExceptionResponse`에는 timeStamp, message, details를 인자로 받도록 우리가 구현하였다.. 
- 기존의 첫번째 인자인 `MethodArgumentNotValidException`로 발생한 exception 객체가 전달된다. 디테일한 정보는 여기에서 얻을 수 있다.
- 따라서 message에는 사용자 지정 String인 "validation Failed"를 출력하도록 했다.
- 해당 예외 Response를 반환한다.

<br>

#### 결과

![img.png](img.png)
- message에 앞서 전달한 내용이 출력된다.
- default message는 User 객체 내의 message에서 변경할 수 있다.
````java
    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;
````

#### 과제 
- 다음 API에도 검증을 추가해보자.
- id에 숫자가 아닌 값이 들어왔을 경우 예외를 반환해본다.
````java
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }
````