package az.elvin.constructionAdmin.service;


import az.elvin.constructionAdmin.dto.CategoryDto;
import az.elvin.constructionAdmin.dto.SizeDto;
import az.elvin.constructionAdmin.dto.SizeListDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.entity.CategoryEntity;
import az.elvin.constructionAdmin.entity.SizeEntity;
import az.elvin.constructionAdmin.mapper.SizeMapper;
import az.elvin.constructionAdmin.repo.CategoryRepository;
import az.elvin.constructionAdmin.repo.SizeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
public class SizeService {
    private final SizeRepository sizeRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper;

    public SizeService(SizeRepository sizeRepository, CategoryRepository categoryRepository, ObjectMapper mapper) {
        this.sizeRepository = sizeRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    public Map<String, Object> getSizes(int page, String perPage) {
        log.info("*****Get Size List Service*****");
        List<SizeListDto> sizeList = new LinkedList<>();

        Page<SizeEntity> getSizeEntities = sizeRepository.findAllByActiveOrderByNameAsc(ACTIVE.ordinal(),
                PageRequest.of(page - 1, getPerPage(perPage)));

        getSizeEntities.forEach(size -> {
            String actions = "";
            if (size.getActive().equals(ACTIVE.ordinal())) {
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill sizeEdit\" data-id=\"" + size.getId() + "\" title=\"Edit\"><i class=\"la la-edit\"></i></a>";
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill sizeDelete\" data-id=\"" + size.getId() + "\" title=\"Delete\"><i class=\"la la-trash\"></i></a>";
            }
            SizeListDto sizeListDto = SizeListDto.builder()
                    .name(size.getName())
                    .category(size.getCategory().getName())
                    .actions(actions)
                    .build();
            sizeList.add(sizeListDto);
        });
        long sizeCount = sizeRepository.countAllByActive(ACTIVE.ordinal());
        return createDataTable(sizeList, sizeCount, page, perPage);

    }

    public CommonResponse addSize(SizeDto sizeDto) throws JsonProcessingException {
        log.info("*****Add Size Service*****");
        log.info(String.format("sizeDto = %s", sizeDto));

        SizeEntity sizeEntity = SizeMapper.INSTANCE.dtoToEntity(sizeDto);
        sizeRepository.save(sizeEntity);
        return success();

    }

    public CommonResponse getSizeById(Long id) {
        log.info("*****Get Size By Id Service*****");
        log.info(String.format("id = %s", id));

        SizeEntity sizeEntity = sizeRepository.findById(id).get();

        SizeDto sizeDto = SizeMapper.INSTANCE.entityToDtoById(sizeEntity);

        return success(sizeDto);
    }

    public CommonResponse editSize(SizeDto sizeDto) throws JsonProcessingException {
        log.info("*****Edit Size Service*****");
        log.info(String.format("sizeDto = %s", sizeDto));

        SizeEntity sizeEntity = sizeRepository.findById(sizeDto.getId()).get();
        sizeEntity.setName(sizeDto.getName());
        sizeRepository.save(sizeEntity);
        return success();
    }


    public CommonResponse deleteSize(Long id) {
        log.info("*****Delete Size Service*****");
        log.info(String.format("id = %s", id));

        SizeEntity sizeEntity = sizeRepository.findById(id).get();
        sizeEntity.setActive(DEACTIVE.ordinal());
        sizeRepository.save(sizeEntity);
        return success();
    }

    public CommonResponse getCategoryList() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByActive(ACTIVE.ordinal());
        List<CategoryDto> categoryDtos = new LinkedList<>();
        categoryEntities.forEach(categoryEntity -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryEntity.getId());
            categoryDto.setNameAz(categoryEntity.getName());
            categoryDtos.add(categoryDto);
        });
        return success(categoryDtos);
    }
}
