package spring.springmvcbasic.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.springmvcbasic.basic.SampleData;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        SampleData sampleData = objectMapper.readValue(messageBody, SampleData.class);
        log.info("username={}, age={}", sampleData.getUsername(), sampleData.getAge());

        response.getWriter().write("OK");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJson2(@RequestBody String messageBody) throws IOException {
        /*
        @RequestBody로 요청 메시지의 바디만 파라미터로 받아와 핸들링
        @ResponseBody로 응답 메시지 바디에 들어갈 문자열만 메서드에서 반환
        */

        SampleData sampleData = objectMapper.readValue(messageBody, SampleData.class);
        log.info("username={}, age={}", sampleData.getUsername(), sampleData.getAge());

        return "OK";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJson3(@RequestBody SampleData sampleData) {
        /*
        요청 메시지 바디에 담긴 JSON 데이터와 매핑할 객체를 직접 파라미터로 받아올 수 있음
        이 경우, 내부적으로 HttpMessageConverter가 요청 바디에 들어온 데이터를 지정된 객체와 매핑해 변환해줌
        (컨버터는 인터페이스고, 실제로 JSON을 객체로 매핑해주는 구현체는 MappingJackson2HttpMessageConverter임)
        따라서 V2에서처럼 ObjectMapper를 사용해서 직접 매핑해주지 않아도 됨
        */

        log.info("username={}, age={}", sampleData.getUsername(), sampleData.getAge());

        return "OK";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJson4(HttpEntity<SampleData> httpEntity) {
        /*
        V3와 마찬가지인데, 요청 메시지를 대변하는 HttpEntity 객체를 파라미터로 받아와 getBody()로 꺼내는 방식
        HttpEntity에 제네릭으로 SampleData 타입을 지정했기 때문에 바디가 SampleData 타입이 됨
        */

        SampleData sampleData = httpEntity.getBody();
        log.info("username={}, age={}", sampleData.getUsername(), sampleData.getAge());

        return "OK";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public SampleData requestBodyJson5(@RequestBody SampleData sampleData) throws IOException {
        /*
        JSON으로 받아온 데이터를 다시 JSON으로 반환
        내부적으로는 "JSON(요쳥) -> SampleData 객체 -> JSON(응답)"의 변환 과정을 거친 것임
        (변환에는 항상 HttpMessageConverter가 관여함)

        단, 응답을 받을 클라이언트의 HTTP 요청 헤더에 Accept 옵션이 application/json 이어야함
        */

        log.info("username={}, age={}", sampleData.getUsername(), sampleData.getAge());

        return sampleData;
    }

}
