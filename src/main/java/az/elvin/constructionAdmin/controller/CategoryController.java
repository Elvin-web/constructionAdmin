package az.elvin.constructionAdmin.controller;

import az.elvin.constructionAdmin.dto.CategoryDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/category")
    private String category() {
        return "categories";
    }

    @GetMapping(value = "/categories")
    @ResponseBody
    public Map<String, Object> getCategories(@RequestParam(value = "datatable[pagination][page]") int page,
                                             @RequestParam(value = "datatable[pagination][perpage]", required = false) String perPage) {
        return categoryService.getCategories(page, perPage);
    }


    @PostMapping(value = "/categories")
    @ResponseBody
    public CommonResponse addCategory(CategoryDto categoryDto) throws JsonProcessingException {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping(value = "/categories/{id}")
    @ResponseBody
    public CommonResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping(value = "/categories")
    @ResponseBody
    public CommonResponse editCategory(CategoryDto categoryDto) throws JsonProcessingException {
        return categoryService.editCategory(categoryDto);
    }

    @GetMapping(value = "/category-delete/{id}")
    @ResponseBody
    public CommonResponse deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

}
