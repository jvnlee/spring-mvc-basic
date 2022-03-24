package spring.springmvcbasic.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("OK");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyString2(InputStream inputStream, Writer responseWriter) throws IOException {
        /*
        서블릿 요청/응답 객체를 파라미터로 받지 않아도 됨
        요청 객체에서 받아올 InputStream과, 응답 객체에서 받아올 Writer를 직접 받아올 수 있기 때문에 V1에 비해 코드 간결화
         */

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("OK");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyString3(HttpEntity<String> httpEntity) {
        /*
        InputStream과 Writer를 사용하지 않고 HttpEntity라는 HTTP 메시지를 대변하는 객체를 가지고 요청과 응답 핸들링
        파라미터로 넘어온 HttpEntity는 요청 메시지이므로 getBody()로 바디의 데이터를 읽어옴.
        응답할 때도 새 HttpEntity 인스턴스를 만들어 반환함. 생성자에 원하는 바디 내용을 넣어 응답할 수 있음.

        HttpEntity는 헤더와 바디를 편리하게 핸들링하는 수단이므로, 요청 파라미터를 조회하는 기능과는 관련 없음
        요청 파라미터 핸들링은 @RequestParam, @ModelAttribute로 해야함
        */

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("OK");
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyString4(@RequestBody String messageBody) {
        /*
        HttpEntity에서도 결국은 바디에 관심이 있는 것이므로
        HTTP 요청 메시지에 대해 @RequestBody 어노테이션으로 아예 바디 자체를 파라미터로 받아올 수 있음
        @ResponseBody를 메서드 레벨에 붙이면 반환하는 문자열을 바로 바디에 넣어 응답 메시지를 만들 수 있음
        */
        log.info("messageBody={}", messageBody);

        return "OK";
    }

}
