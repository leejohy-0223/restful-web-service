# Spring Boot를 이용한 RESTful Web Services 개발

## 1. Web Service & Web Application

<br>

### Web Service 개발 방법 SOAP과 REST의 이해
- 네트워크 상에서 서로 다른 종류의 컴퓨터 간에 상호작용하기 위한 소프트웨어 시스템
- 3 keys
    - Designed for machine-to-machine (or app to app) 상호작용
    - 플랫폼 독립적인 구조
    - 어플리케이션 간 네트워크 통신 지원

<br>

#### Web Application
- web mail, retail sales, banking과 같은 프로그램
- 구조는 client -> web server(아파치) -> web application server(톰캣) -> database로 구성된다.

<br>

#### Web Service
- xml, json의 형식으로 클라이언트 <-> web service 간 request / response 를 주고 받는다.
- 웹서비스에 요청을 전달(클라이언트) / 응답(서버)
- 이러한 Web Service를 가발하기 위해 SOAP, RESTful을 사용할 수 있다.

<br>

#### SOAP (Simple Object Access Protocol)
- 우리가 사용할 수 있는 http, https, smtp와 같은 프로토콜을 이용하여 xml 기반의 메시지를 전달할 수 있는 시스템
- envelope - header - body로 구성되어있다.
- 간단한 정보를 제공하기 위해서 오버헤드가 심하기 때문에 개발하기 어려운 단점이 있다.

<br>

#### REST(REpresentational State Transfer)
- 상태를 전달하는 것(컴퓨터가 가지고있는 자원의 상태를 의미 - 파일, 데이터)
- SOAP의 대체로, 최근에는 이 방식을 사용한다.
- 프로그램, 플랫폼에 독립적.
- Resource의 Representation에 의한 상태 전달
- HTTP 메서드를 통해 Resource를 처리하기 위한 아키텍쳐

<br>

#### RESTful
- REST API를 제공하는 웹 서비스
- 이를 개발하기 위해 HTTP 프로토콜을 사용하는 애플리케이션이 필요
    - 일반적으로 웹 브라우저가 사용
    - Postman, curl과 같은 툴을 사용하기도 한다.
- URI : 인터넷 자원을 나타내는 유일한 주소
- 문서 포맷 : xml, html, json

<br>

#### SOAP vs REST를 선택하는 기준
- 접근 제한성 vs 시스템 아키텍쳐 구조
- 사용되는 데이터 문서 포맷
- 서비스를 정의하는 방법
- 전송되는 방법 / 규약
- 구현 방법

#### Q & A
- SOAP, REST가 아닌 HTTP API를 부르는 명칭?
    - REST와 일반 HTTP는 약간 차이가 있다(반환하는 문서의 데이터 타입(XML, JSON), 지원하는 http method의 종류 등)
    - HTTP API는 HTTP 전송 프로토콜을 사용하는 모든 API이다. 이 안에 Restful, SOAP가 포함되어있을 수 있다.
    - 즉, HTTP API가 이들을 포괄하는 개념이다.

<br>

## 2. Spring Boot로 개발하는 RESTful Service

<br>

### Spring Boot 개요

- 단독 실행 가능한 스프링 base 웹 어플리케이션을 만들 수 있다. 스프링 부트에 이미 내장형 tomcat, jetty 등의 was를 가지고 있기 때문에, 별도의 was를 설치할 필요가 없다는 의미이다.
- 최소한의 설정 작업을 통해 third party libraries를 사용할 수 있다.(auto config)
- 스프링 프레임워크 실행에 필요한 많은 api들을 'starter'를 통해 쉽게 사용할 수 있다.
- 추가적인 xml, annotation 필요가 없어진다.
- 모니터링 작업, 다양한 라이브러리를 포함
- https://start.spring.io/를 통해 스프링 부트 프로젝트를 생성할 수 있다.

<br>

#### Spring Boot : Step 0
- 스프링 부트 어플리케이션은 main 클래스를 생성하면 된다.
- Spring Boot Application 어노테이션을 가지고 있다.
- Auto Configuration
- Component Scan
- Controller, Repository 등의 클래스의 인스턴스를 스프링 컨테이너 메모리로 가져와서 애플리케이션에서 사용할 수 있는 bean 형태로 관리(IoC)

<br>

### REST API 설계
- user : post = 1 : N 관계로 설계해보자.
- 간단한 URI를 정의하고, 그에 맞는 HTTP Method를 통해 API를 설계한다.
- 이러한 간단함이 SOAP 개발 방식보다 각광받는 이유이다.

<br>

### Spring Boot Project 생성
- 단순히 크롬에서 get / post는 요청할 수 있지만 그 외 메서드를 사용하기 위해 postman 또는 curl을 사용한다.

<br>

### HelloWorld Controller 추가
- RestController 클래스 사용
- 기존에는 RequestMapping -> GetMapping 등으로 어떠한 http method를 사용할 것인지 바로 지정
- get, post는 웹 브라우저를 통해 바로 서비스를 사용해볼 수 있다.

<br>

### HelloWorld Bean 추가
- 문자열이 아닌 자바 빈 형태로 반환 받아보자.
- @Data, @AllArgsConstructor 를 통해 기본 메서드 및 생성자를 자동으로 만들 수 있다.
- LOMBOK 이라는 플러그인을 인텔리제이에 추가해야, 정상적으로 에러를 보여주게 된다.
- @RestController로 인해 Bean을 json 형태로 반환받게 된다.
- ResponseBody 가 필요없어진다.

<br>

### Dispatcher Servlet과 프로젝트 동작의 이해

#### Spring Boot 동작 원리
- application.yml 또는 application.properties를 통해 설정이름 : 값 쌍으로 설정을 할 수 있다.
- yml을 통해 모든 데이터를 list, hash scalar 데이터의 조합으로 사용할 수 있으며, 가독성이 좋아 환경 설정 등으로 자주 사용된다.
- Spring Boot Auto Configuration
    - DispatcherServletAutoConfiguration : DispatcherServlet을 작동시키기 위한 기본 configuration
    - HttpMessageConvertorsAutoConfiguration : 기본적으로 json으로 데이터를 변환시켜 클라로 반환하고 있다.
    
<br>

#### DispatcherServlet : "/"
- dispatch : (특정 목적을 위해)보내다[파견하다].
- 클라이언트의 모든 요청을 한 곳으로 받아서 처리
- 요청에 맞는 handler로 요청을 전달
- handler의 실행 결과를 http response 형태로 만들어서 반환한다.
- 다음 순서로 실행된다.
    1. DispatcherServlet이 요청을 받는다.
    2. HandlerMapping을 통해 Controller를 찾는다.
    3. uri에 해당하는 Controller가 ModelAndView를 생성하여 반환한다.
    4. ViewResolver에서 해당 ModelAndView를 처리할 수 있는 View를 찾아준다. 여기에서 페이지를 만든다.
    5. 마지막으로 View에서 해당 페이지에 ModelAndView를 합쳐 반환한다.
  
- Spring4부터 @RestController 지원(@Controller + @ResponseBody)
- View를 갖지 않는 REST Data(JSON/XML)를 반환. 즉 사용자에게 보여지는 view가 아닌 데이터만 반환할 필요가 있다!

<br>

### Path Variable 사용 
- api url에 변수 지정해서 활용해보자.
- 원래 api는 말 그대로 api이기 때문에, program 간 사용되는 약속이다.
- uri에 가변적인 변수를 지정해서 사용할 수 있다.
- 기존 spring mvc에서도 사용되는 방식이다.
- Chrome plugin 설치를 통해 text처럼 보이는 json을 직관적인 형태로 볼 수 있다. json Viewer를 설치해서 적용한다.

<br>

## 3. User Service API 구현

### User 도메인 클래스 생성
- Domain : 인간 활동 영역, 자율적인 컴퓨터 활동 영역 등 특정한 부분에서 사용되는 업무 지식이라고 생각하자.
- Spring Container(IoC Container)에 등록된 bean은 실행 중 개발자가 변경할 수 없기 떄문에 일관성 있는 instance를 사용할 수 있다.

### 사용자 목록 조회를 위한 API 구현 - GET HTTP Method
- 아래와 같이 로그를 통해 의존성 주입을 확인할 수 있다.(Controller에 Service를 주입)  
   Autowiring by type from bean name 'userController' via constructor to bean named 'userDaoService'
  
- 검색되지 않는 id는 null을 반환하도록 구현하였다.

<br>

### 사용자 목록 조회를 위한 API 구현 - POST HTTP Method
- post : 웹브라우저로 사용은 가능하지만, html작업 또는 js, jquery와 같은 추가 작업이 필요하다.
- 우리는 restAPI를 만들거기 때문에 html 따위는 없다.
- postman을 통해 body - rawtype(json)을 지정하고, json 형식으로 post요청을 보내면 저장된다.

<br>

### HTTP Status Code 제어
- ServletUriComponentBuilder 를 통해 Status Code를 제어하는 방법을 알아보자.
    - REST API를 구현하다보면 사용자로부터 요청왔을 때 특정한 값을 포함한 uri를 전달해야 하는 상황이 발생할 수 있다.(헤더의 Location에 uri를 전달하는 등)
    - 이 때 ServletUriComponentBuilder를 통해 적잘한 URI를 만들고 요청한 사용자에게 특정 값을 포함한 uri를 전달할 수 있다.
    - Status code가 201 Created로 변경된다. 이를 통해 적절한 REST API를 만들 수 있다.
    - ResponseEntity는 HttpEntity(Http 메시지 헤더, 바디 정보 가짐)를 상속받으며, 여기에 추가로 응답 코드를 설정할 수 있다.
- ResponseEntity의 빌더 패턴을 사용하여 상태, 헤더, body를 지정 후 반환할 수 있다.
- 또는 HttpHeader를 정의한 후 ResponseEntity의 생성자를 통해 상태를 지정후 반환하는 방법도 있다.  
- 하나의 단일되어있는 200 대신, 적절한 응답값을 전달하는게 REST한 설계이다.
- 또한 post 이후 추가 정보를 제공함으로써 네트워크 비용을 줄일 수 있게 된다.

<br>

### HTTP Status Code 제어를 위한 Exception Handling
- 클라이언트가 유효하지 않는 요청을 할 때 예외를 발생시키도록 구현해보자.
- controller에서, 사용자가 요청한 id가 없을 경우 RuntimeException을 Throw 하도록 구현하였다. 결과로 500번 에러가 출력되며, trace에 서버 정보들이 그대로 노출되었다.
- Exception에 @ResponseStatus를 통해 Status를 지정할 수 있다. 다만 trace에 서버 측 정보는 동일하게 노출된다.
  
<br> 

#### checked, unchecked 예외 관련
- 예외 복구 전략이 명확하고 그것이 가능하다면 Checked Exception을 try catch로 잡고, 해당 복구를 하는것이 좋다.
- 하지만 그러한 경우는 흔하지 않으며, Checked Exception이 발생하면 더 구체적인 Unchecked Exception을 발생시키고, 예외에 대한 메시지를 명확히 전달하는게 효과적이다.
- 무책임하게 상위 메서드로 throw를 던지는 행위는 안하는게 좋다. 상위 메서드의 책임이 그만큼 늘어나기 때문.
- checked Exception은 기본 트랜잭션 속성에서는 rollback을 진행하지 않는 점을 기억하자.
- 참고 : https://cheese10yun.github.io/checked-exception/

<br>

### Spring AOP를 이용한 Exception Handling
- 특정한 예외 클래스 말고, 일반화된 예외 클래스를 사용해보자.
- ExceptionResponse 예외 클래스 생성
- Handler class를 추가로 생성한다. controller에서 예외가 발생하면, handler 예외 클래스가 발생할 수 있도록 처리해보자.
- 스프링에서 로깅, 로그인 정보, 어떤 메시지를 추가하는 정보와 같이, 항상 실행시켜줘야 하는 공통 항목은 AOP를 통해 공통 로직을 사용할 수 있도록 만들 수 있다.

#### Response 예외 처리를 담당하는 ResponseEntityExceptionHandler
- 해당 클래스를 확장한 컨트롤러에 Response 예외처리 담당을 부여할 수 있다.
- @RestController + @ControllerAdvice = @RestControllerAdvice와 동일 역할이며, 이를 통해 AOP를 적용할 수 있다.
- 강의의 CustomizedResponseEntityExceptionHandler는 결국 에러 핸들링 용도이므로 @RestController를 생략해도 된다.
- 최종 ResponseEntity는 컨트롤러에서 반환된다. 따라서 앞서 적용했던 UserNotFoundException의 @ResponseStatus에서 적용한 응답 코드는 덮어씌워진다.
- @ExceptionHandler(예외 클래스)를 메서드 위에 작성한다. 예외 클래스의 범위를 작성(Exception.class 또는 custom 예외)하여, 발생 예외마다 처리하는 handler를 만들 수 있다.
- @ControllerAdvice만 적용했음에도, 반환된 ResponseEntity가 json 형식으로 출력된다. 이유는 뭘까?
    - @ResponseBody는 HttpMessageConverter를 통해 응답 값을 자동으로 json으로 직렬화한 후 응답해주는 역할을 한다. 이 때는 view Resolver 대신 HttpMessageConverter가 동작한다.
    - HttpEntity, ResponseEntity는 HttpMessageConverter로 컨버팅된다.
    - 다른 컨버터를 등록하지 하지 않았으면 기본적으로 HttpMessageConveter(얘는 인터페이스이다)의 구현체인 MappingJackson2HttpMessageConverter를 사용한다.
    - AbstractJackson2HttpMessageConverter의 writeInternal에 의해서 호출된다.
    - ObjectMappers 등 Jackson라이브러리를 통해 Json으로 변환하는 것을 확인할 수 있다!
    - 즉, ResponseEntity는 메시지 컨버터에 의해 기본 json으로 반환된다.
    - Spring MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.
         - HTTP 요청 : @RequestBody, HttpEntity(RequestEntity) : 컨트롤러가 실행되기 전에, 이들에 대해 HTTP 메시지 컨버터를 적용해서 컨트롤러로 넘긴다.
         - HTTP 응답 : @ResponseBody, HttpEntity(ResponseEntity) : 컨트롤러의 리턴값을 HTTP 메시지 컨버터를 적용해서 응답에 넣는 역할을 한다.
- HTTPMessageConverter는 뷰를 거치지 않고 데이터를 반환하는 처리 방식이다. byte 데이터 처리(ByteArrayHttpMessageConverter), String 데이터 처리(StringHTTPMessageConverter), 
  객체 타입 데이터 처리(MappingJackson2HttpMessageConverter)를 수행한다.
  
<br>

#### 사용자 삭제를 위한 API 구현 - DELETE HTTP METHOD
- 수정하기 : PUT을 통해 업데이트 가능하다. POST랑 동일하게 파라미터를 넘기면 된다.
- REST API : GET, POST, PUT, DELETE만 지원





