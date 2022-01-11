package az.elvin.constructionAdmin.controller;

import az.elvin.constructionAdmin.dto.SizeDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.service.SizeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping("/size")
    private String size() {
        return "sizes";
    }

    @GetMapping(value = "/sizes")
    @ResponseBody
    public Map<String, Object> getSizes(@RequestParam(value = "datatable[pagination][page]") int page,
                                             @RequestParam(value = "datatable[pagination][perpage]", required = false) String perPage) {
        return sizeService.getSizes(page, perPage);
    }

    @PostMapping(value = "/sizes")
    @ResponseBody
    public CommonResponse addSize(SizeDto sizeDto) throws JsonProcessingException {
        return sizeService.addSize(sizeDto);
    }

    @GetMapping(value = "/sizes/{id}")
    @ResponseBody
    public CommonResponse getSizeById(@PathVariable Long id) {
        return sizeService.getSizeById(id);
    }

    @PutMapping(value = "/sizes")
    @ResponseBody
    public CommonResponse editSize(SizeDto sizeDto) throws JsonProcessingException {
        return sizeService.editSize(sizeDto);
    }

    @GetMapping(value = "/size-delete/{id}")
    @ResponseBody
    public CommonResponse deleteSize(@PathVariable Long id) {
        return sizeService.deleteSize(id);
    }

    @GetMapping(value = "/category-list")
    @ResponseBody
    public CommonResponse getCategoryList() {
        return sizeService.getCategoryList();
    }
}
