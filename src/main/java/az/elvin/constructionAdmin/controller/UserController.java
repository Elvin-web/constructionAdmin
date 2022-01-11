package az.elvin.constructionAdmin.controller;

import az.elvin.constructionAdmin.dto.UserDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    private ModelAndView profile() {
        ModelAndView model = new ModelAndView("profile");
        model.addObject("user", userService.profile().getData());
        return model;
    }

    @GetMapping("/user")
    private String user() {
        return "users";
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public Map<String, Object> getUsers(@RequestParam(value = "datatable[pagination][page]") int page,
                                        @RequestParam(value = "datatable[pagination][perpage]", required = false) String perPage) {
        return userService.getUsers(page, perPage);
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public CommonResponse addUser(UserDto userDto) throws IOException {
        return userService.addUser(userDto);
    }

    @GetMapping(value = "/users/{id}")
    @ResponseBody
    public CommonResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/users")
    @ResponseBody
    public CommonResponse editUser(@RequestParam("id") Long id, UserDto userDto) throws IOException {
        return userService.editUser(id, userDto);
    }

    @GetMapping(value = "/user-delete/{id}")
    @ResponseBody
    public CommonResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping(value = "/roles")
    @ResponseBody
    public CommonResponse getRoles() {
        return userService.getRoleList();
    }
}
