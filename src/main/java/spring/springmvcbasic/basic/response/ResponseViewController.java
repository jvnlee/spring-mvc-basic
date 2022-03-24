package spring.springmvcbasic.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        /*
        Thymeleaf로 만든 동적인 뷰 템플릿은 "resources/templates"를 기본 경로(prefix)로 가짐
        기본 suffix는 ".html"이기 때문에 뷰의 논리 이름 뒤에는 ".html"이 붙음
        ModelAndView 객체를 생성할 때, 뷰의 논리 이름(하위 경로)를 지정해주면 해당 뷰가 렌더링됨
        */

        return new ModelAndView("response/hello")
                .addObject("data", "hello");
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        /*
        ModelAndView 객체를 반환하지 않고, 뷰의 논리 이름 문자열만 반환하는 방식
        이 때는 파라미터로 Model을 받아와 Model에 동적인 데이터인 attribute를 추가해줘야함
        */

        model.addAttribute("data", "hello");

        return "response/hello";
    }

}
