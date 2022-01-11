package az.elvin.thread.v3.controller;

import az.elvin.thread.dto.respone.CommonResponse;
import az.elvin.thread.v3.service.ThreadV2Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ThreadV2Controller {

    private final ThreadV2Service threadV2Service;

    public ThreadV2Controller(ThreadV2Service threadV2Service) {
        this.threadV2Service = threadV2Service;
    }

    @GetMapping(value = "/thread-list-v2")
    @ResponseBody
    public CommonResponse getThreadRepoV2List() {
        return threadV2Service.getThreadRepoList();
    }

}
