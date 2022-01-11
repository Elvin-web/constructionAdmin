package az.elvin.constructionAdmin.controller;

import az.elvin.constructionAdmin.dto.BrendDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.service.BrendService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class BrendController {

    private final BrendService brendService;

    public BrendController(BrendService brendService) {
        this.brendService = brendService;
    }

    @GetMapping("/brend")
    private String brend() {
        return "brends";
    }

    @GetMapping(value = "/brends")
    @ResponseBody
    public Map<String, Object> getBrends(@RequestParam(value = "datatable[pagination][page]") int page,
                                             @RequestParam(value = "datatable[pagination][perpage]", required = false) String perPage) {
        return brendService.getBrends(page, perPage);
    }


    @PostMapping(value = "/brends")
    @ResponseBody
    public CommonResponse addBrend(BrendDto brendDto) throws JsonProcessingException {
        return brendService.addBrend(brendDto);
    }

    @GetMapping(value = "/brends/{id}")
    @ResponseBody
    public CommonResponse getBrendById(@PathVariable Long id) {
        return brendService.getBrendById(id);
    }

    @PutMapping(value = "/brends")
    @ResponseBody
    public CommonResponse editBrend(BrendDto brendDto) throws JsonProcessingException {
        return brendService.editBrend(brendDto);
    }

    @GetMapping(value = "/brend-delete/{id}")
    @ResponseBody
    public CommonResponse deleteBrend(@PathVariable Long id) {
        return brendService.deleteBrend(id);
    }

}
