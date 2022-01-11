package az.elvin.constructionAdmin.service;


import az.elvin.constructionAdmin.dto.CategoryDto;
import az.elvin.constructionAdmin.dto.CategoryListDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.dto.respone.Description;
import az.elvin.constructionAdmin.entity.CategoryEntity;
import az.elvin.constructionAdmin.repo.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static az.elvin.constructionAdmin.dto.respone.CommonResponse.success;
import static az.elvin.constructionAdmin.enums.ActiveEnum.ACTIVE;
import static az.elvin.constructionAdmin.enums.ActiveEnum.DEACTIVE;
import static az.elvin.constructionAdmin.util.CommonUtil.createDataTable;
import static az.elvin.constructionAdmin.util.CommonUtil.getPerPage;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper;


    public CategoryService(CategoryRepository categoryRepository, ObjectMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public Map<String, Object> getCategories(int page, String perPage) {
        log.info("*****Get Category List Service*****");
        List<CategoryListDto> categoryList = new LinkedList<>();

        Page<CategoryEntity> getCategoryEntities = categoryRepository.findAllByActiveOrderByIdDesc(ACTIVE.ordinal(),
                PageRequest.of(page - 1, getPerPage(perPage)));

        getCategoryEntities.forEach(category -> {
            String actions = "";
            if (category.getActive().equals(ACTIVE.ordinal())) {
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill categoryEdit\" data-id=\"" + category.getId() + "\" title=\"Edit\"><i class=\"la la-edit\"></i></a>";
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill categoryDelete\" data-id=\"" + category.getId() + "\" title=\"Delete\"><i class=\"la la-trash\"></i></a>";
            }
            Description description;
            try {
                description = mapper.readValue(category.getDescription(), Description.class);
            } catch (JsonProcessingException e) {
                description = new Description();
            }
            CategoryListDto categoryListDto = CategoryListDto.builder()
                    .nameAz(description.getAz())
                    .nameEn(description.getEn())
                    .nameRu(description.getRu())
                    .actions(actions)
                    .build();
            categoryList.add(categoryListDto);
        });
        long categoryCount = categoryRepository.countAllByActive(ACTIVE.ordinal());
        return createDataTable(categoryList, categoryCount, page, perPage);

    }

    public CommonResponse addCategory(CategoryDto categoryDto) throws JsonProcessingException {
        log.info("*****Add Category Service*****");
        log.info(String.format("categoryDto = %s", categoryDto));

        Description description = Description.builder()
                .az(categoryDto.getNameAz())
                .en(categoryDto.getNameEn())
                .ru(categoryDto.getNameRu())
                .build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryDto.getNameAz())
                .description(mapper.writeValueAsString(description))
                .active(ACTIVE.ordinal())
                .dataDate(LocalDateTime.now())
                .build();
        categoryRepository.save(categoryEntity);
        return success();
    }

    public CommonResponse getCategoryById(Long id) {
        log.info("*****Get Settlement By Id Service*****");
        log.info(String.format("id = %s", id));

        CategoryEntity categoryEntity = categoryRepository.findById(id).get();

        Description description;
        try {
            description = mapper.readValue(categoryEntity.getDescription(), Description.class);
        } catch (JsonProcessingException e) {
            description = new Description();
        }
        CategoryDto categoryDto = CategoryDto.builder()
                .id(categoryEntity.getId())
                .nameAz(description.getAz())
                .nameEn(description.getEn())
                .nameRu(description.getRu())
                .build();
        return success(categoryDto);
    }

    public CommonResponse editCategory(CategoryDto categoryDto) throws JsonProcessingException {
        log.info("*****Edit Settlement Service*****");
        log.info(String.format("categoryDto = %s", categoryDto));

        Description description = Description.builder()
                .az(categoryDto.getNameAz())
                .en(categoryDto.getNameEn())
                .ru(categoryDto.getNameRu())
                .build();

        CategoryEntity categoryEntity = categoryRepository.findById(categoryDto.getId()).get();
        categoryEntity.setName(categoryDto.getNameAz());
        categoryEntity.setDescription(mapper.writeValueAsString(description));
        categoryRepository.save(categoryEntity);
        return success();
    }


    public CommonResponse deleteCategory(Long id) {
        log.info("*****Delete v Service*****");
        log.info(String.format("id = %s", id));

        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        categoryEntity.setActive(DEACTIVE.ordinal());
        categoryRepository.save(categoryEntity);
        return success();
    }
}
