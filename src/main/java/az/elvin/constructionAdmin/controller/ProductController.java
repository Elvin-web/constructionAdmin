package az.elvin.constructionAdmin.controller;

import az.elvin.constructionAdmin.dto.ProductDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/product")
    private String product() {
        return "products";
    }

    @GetMapping(value = "/products")
    @ResponseBody
    public Map<String, Object> getProducts(@RequestParam(value = "datatable[pagination][page]") int page,
                                           @RequestParam(value = "datatable[pagination][perpage]", required = false) String perPage) {
        return productService.getProducts(page, perPage);
    }

    @PostMapping(value = "/products")
    @ResponseBody
    public CommonResponse addProduct(ProductDto productDto) throws JsonProcessingException {
        return productService.addProduct(productDto);
    }

    @GetMapping(value = "/products/{id}")
    @ResponseBody
    public CommonResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping(value = "/products")
    @ResponseBody
    public CommonResponse editProduct(@RequestParam("id") Long id, ProductDto productDto) throws JsonProcessingException {
        return productService.editProduct(id,productDto);
    }

    @GetMapping(value = "/product-delete/{id}")
    @ResponseBody
    public CommonResponse deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping(value = "/brend-list")
    @ResponseBody
    public CommonResponse getBrendList() {
       return productService.getBrendList();
    }

    @GetMapping(value = "/colour-list")
    @ResponseBody
    public CommonResponse getColourList() {
        return productService.getColourList();
    }
    @GetMapping(value = "/country-list")
    @ResponseBody
    public CommonResponse getCountryList() {
        return productService.getCountryList();
    }
    @GetMapping(value = "/size-list")
    @ResponseBody
    public CommonResponse getSizeList() {
        return productService.getSizeList();
    }

    @GetMapping(value = "/having_status-list")
    @ResponseBody
    public CommonResponse getHavingStatusList() {
        return productService.getHavingStatusList();
    }

}
