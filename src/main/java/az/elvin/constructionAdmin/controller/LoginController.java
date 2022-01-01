package az.elvin.constructionAdmin.controller;

import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.enums.ResponseEnum;
import az.elvin.constructionAdmin.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "/")
    public String loginRedirect() {
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password, HttpSession session) {
        CommonResponse response = loginService.login(username, password);
        session.setAttribute("user", response.getData());

        if (response.getStatusCode().equals(ResponseEnum.SUCCESS.ordinal())) {
            return "redirect:/profile";
        } else {
            return "login/?error=true";
        }
    }
}
