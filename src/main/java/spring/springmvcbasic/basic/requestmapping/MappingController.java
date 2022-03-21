package spring.springmvcbasic.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "OK";
    }

    /*
    Path Variable (경로 변수)
    HTTP API를 설계할 때 리소스 경로에 식별자를 넣는 스타일이 자주 사용됨
    경로에 {}로 템플릿화한 변수를 @PathVariable("변수명")으로 꺼내올 수 있음
    */
    @GetMapping("/mapping/{userId}")
    public String mappingPathVar(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "OK";
    }

    // Path Variable 다중 매핑
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingMultiPathVar(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingMultiPathVar userId={}, orderId={}", userId, orderId);
        return "OK";
    }

    // headers 옵션에 들어간 key-value가 HTTP 요청 헤더에 포함되어야만 매핑
    @GetMapping(value = "/mapping-headers", headers = "andy=cool")
    public String mappingHeaders() {
        log.info("mappingHeaders");
        return "OK";
    }

    // consumes 옵션이 HTTP 요청 헤더의 Content-Type의 값과 일치하는 경우만 매핑
    @PostMapping(value = "/mapping-consumes", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "OK";
    }

    // produces 옵션이 HTTP 요청 헤더의 Accepts의 값과 일치하는 경우만 매핑
    @PostMapping(value = "/mapping-produces", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "OK";
    }

}
