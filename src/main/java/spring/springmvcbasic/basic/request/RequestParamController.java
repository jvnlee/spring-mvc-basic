package spring.springmvcbasic.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.springmvcbasic.basic.SampleData;

import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param")
    @ResponseBody
    public String requestParam(String username, Integer age) {
        /*
        원래는 각 요청 파라미터 앞에 @RequestParam("파라미터명") 어노테이션을 붙여야하지만,
        ex) @RequestParam("username") String name

        1. 메서드 파라미터명을 요청 파라미터명과 일치시키면 어노테이션에 명시하는 요청 파라미터명을 생략할 수 있고
           ex) @RequestParam String username
        2. 메서드 파라미터 타입이 기본형 혹은 Wrapper 타입이면 어노테이션 자체를 생략할 수 있음
           ex) String username
        */

        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        /*
        @RequestParam의 required 옵션이 true(기본값)면 요청 파라미터가 누락될 시 400 응답
        false면 누락되어도 그 자리에 null을 채우기 때문에 200 응답

        단, "username="과 같이 값에 빈 문자열이 넘어오는 경우에는 빈 문자열 자체도 값으로 인정하기 때문에 누락되었다고 하지 않음
        (null로 채우지 않음)
        */

        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") int age) {
        /*
        요청 파라미터를 누락시키면, defaultValue 옵션으로 정한 기본값으로 처리해줌
        "username="과 같이 값에 빈 문자열이 넘어오는 경우에도 값이 없는 것으로 간주해서 defaultValue으로 채워줌
        */

        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        /*
        Map으로 받아도 되고 MultiValueMap으로 받아도 됨

        Map은 key-value가 1 to 1 대응이므로 각 파라미터에 들어오는 값이 1개인 것이 확실할 때만 사용
        MultiValueMap은 key-value가 1 to Many 대응이므로 각 파라미터에 들어오는 값이 여러개여도 됨
        (MultiValueMap은 value를 List 형태로 받음)
        */

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "OK";
    }

    @RequestMapping("/model-attribute")
    @ResponseBody
    public String modelAttribute(@ModelAttribute SampleData sampleData) {
        /*
        모델 객체(SampleData)를 파라미터로 받고, @ModelAttribute을 붙여주면 다음 과정이 내부적으로 실행됨

        1. SampleData 객체 생성
        2. 요청 파라미터의 이름으로 SampleData 객체가 가진 프로퍼티(username, age)를 찾음
        3. 부합하는 프로퍼티의 setter를 호출해서 요청 파라미터의 값을 입력(바인딩)함

        @ModelAttribute도 @RequestParam처럼 생략이 가능한데, 아무 어노테이션도 없는 파라미터는 다음과 같은 기준이 적용됨

        1. 기본형, Wrapper 타입인 경우: @RequestParam
        2. 나머지 경우: @ModelAttribute

        단, 나머지 모든 타입의 파라미터를 @ModelAttribute으로 간주하는 것은 아니고, 스프링에서 argument resolver로 미리 지정해둔 타입은 제외임
        (ex. HttpServletResponse)
        */

        log.info("username={}, age={}", sampleData.getUsername(), sampleData.getAge());
        return "OK";
    }

}
