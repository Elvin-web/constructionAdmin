package az.elvin.thread.v1.controller;

import az.elvin.thread.dto.respone.CommonResponse;
import az.elvin.thread.v1.service.ThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ThreadController {

    private final ThreadService threadService;

    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping(value = "/thread-list-repo")
    @ResponseBody
    public CommonResponse getThreadRepoList() {
        return threadService.getThreadRepoList();
    }

}
