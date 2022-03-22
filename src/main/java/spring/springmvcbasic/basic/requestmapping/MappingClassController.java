package spring.springmvcbasic.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    // GET, POST, PATCH, DELETE을 활용해 회원 조회, 등록, 수정, 삭제 API를 간단하게 구현해보기

    @GetMapping
    public String user() {
        return "GET users";
    }

    @PostMapping
    public String addUser() {
        return "POST user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "GET userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "PATCH userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "DELETE userId=" + userId;
    }

}
