package spring.springmvcbasic.basic.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.springmvcbasic.basic.SampleData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
// @Controller
// @ResponseBody
public class ResponseBodyController {

    /*
    @ResponseBody를 메서드마다 반복적으로 붙여주는 번거로움을 피하려면 클래스 레벨에 @ResponseBody를 붙이면 됨
    그런데 @Controller와 @ResponseBody를 합친 것이 @RestController이기 때문에 한방에 해결 가능
     */

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        // ResponseEntity 객체를 반환해서 응답할 때는 상태 코드를 포함시킬 수 있음
        return new ResponseEntity<>("OK", HttpStatus.OK); // 200
    }

    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        /*
        이 경우에는 반드시 @ResponseBody를 붙여줘야 HttpMessageConverter에 의해 JSON으로 변환되어 응답 메시지 바디에 들어감
        붙이지 않으면 반환값을 가지고 뷰 템플릿을 찾으려고 하기 때문에 예외 발생
        */
        return "OK";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<SampleData> responseBodyJsonV1() {
        /*
        ResponseEntity에 JSON으로 변환할 객체의 타입을 지정
        HttpMessageConverter에 의해 객체 형태로 존재하던 데이터가 JSON 형태로 변환되어 응답 메시지 바디에 들어감
        */
        SampleData sampleData = new SampleData();
        sampleData.setUsername("andy");
        sampleData.setAge(20);

        return new ResponseEntity<>(sampleData, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/response-body-json-v2")
    public SampleData responseBodyJsonV2() {
        /*
        ResponseEntity를 사용하지 않고 JSON으로 변환할 객체를 반환시키는 케이스
        이 경우에는 반드시 @ResponseBody를 붙여줘야 HttpMessageConverter에 의해 JSON으로 변환되어 응답 메시지 바디에 들어감
        붙이지 않으면 반환값을 가지고 뷰 템플릿을 찾으려고 하기 때문에 예외 발생

        상태 코드를 추가하려면 @ResponseStatus를 사용해서 지정할 수 있음
        상황에 따른 동적인 상태 코드를 부여하려면 ResponseEntity를 사용해야만함
        */

        SampleData sampleData = new SampleData();
        sampleData.setUsername("andy");
        sampleData.setAge(20);

        return sampleData;
    }

}
