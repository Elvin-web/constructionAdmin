package az.elvin.thread.v3.controller;

import az.elvin.thread.dto.respone.CommonResponse;
import az.elvin.thread.v3.service.ThreadV3Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ThreadV3Controller {

    private final ThreadV3Service threadV3Service;

    public ThreadV3Controller(ThreadV3Service threadV3Service) {
        this.threadV3Service = threadV3Service;
    }

    @GetMapping(value = "/thread-list-v3")
    @ResponseBody
    public CommonResponse getThreadRepoV3List() {
        return threadV3Service.getThreadRepo3List();
    }

}
