package spring.springmvcbasic.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    /*
    @RestController
    HTTP 응답의 바디에 HTML 데이터 대신 컨트롤러 메서드에서 반환하는 문자열을 내보내는 것.
    */

    private final Logger log = LoggerFactory.getLogger(getClass()); // 이 한줄은 @Slf4j 어노테이션으로 대체 가능

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        /*
        application.properties에서 로그 레벨 설정
        - 전체(라이브러리까지 포함):
            logging.level.root=info
        - 특정 패키지와 그 하위 패키지:
            logging.level.spring.springmvcbasic=debug

        로그 레벨
        trace > debug > info > warn > error
        지정한 레벨 이하의 로그들은 모두 포함됨 (ex. 레벨이 trace인 경우는 trace부터 error까지 전부 찍힘)
        보통 개발 서버에서는 로그 레벨을 debug 레벨로 설정하고, 운영 서버에서는 info 레벨로 설정함

        로그에 출력되는 정보
        시간, 로그 레벨, 프로세스 ID, 쓰레드명, 클래스명, 로그 메시지
        */
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "OK";
    }
}
