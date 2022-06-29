package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /* http://localhost:8080/hello */
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";
    }

    /* http://localhost:8080/hello-mvc?name=jisu */
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    /* 컨트롤러를 거치지 않는 단순 정적 페이지는 static 폴더에 html 생성 후 아래와 같이 호출 가능
       http://localhost:8080/hello-static.html */

    /* ResponseBody 를 사용하여 html을 거치지 않고 문자 그대로 출력 */
    @GetMapping("hello-string")
    @ResponseBody   // html body 태그가 아니라 http 응답 body 영역에 문자 내용을 직접 반환
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name + "<h1>html을 거치지 않고 그대로 출력</h1>";
    }

    /* API 방식 {"name":"jisu"} 리턴 */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
