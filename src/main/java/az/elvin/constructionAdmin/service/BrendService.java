package az.elvin.constructionAdmin.service;


import az.elvin.constructionAdmin.dto.BrendDto;
import az.elvin.constructionAdmin.dto.BrendListDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.entity.BrendEntity;
import az.elvin.constructionAdmin.repo.BrendRepository;
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
public class BrendService {
    private final BrendRepository brendRepository;
    private final ObjectMapper mapper;

    public BrendService(BrendRepository brendRepository, ObjectMapper mapper) {
        this.brendRepository = brendRepository;
        this.mapper = mapper;
    }


    public Map<String, Object> getBrends(int page, String perPage) {
        log.info("*****Get Brend List Service*****");
        List<BrendListDto> brendList = new LinkedList<>();

        Page<BrendEntity> getBrendEntities = brendRepository.findAllByActiveOrderByNameAsc(ACTIVE.ordinal(),
                PageRequest.of(page - 1, getPerPage(perPage)));

        getBrendEntities.forEach(brend -> {
            String actions = "";
            if (brend.getActive().equals(ACTIVE.ordinal())) {
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill brendEdit\" data-id=\"" + brend.getId() + "\" title=\"Edit\"><i class=\"la la-edit\"></i></a>";
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill brendDelete\" data-id=\"" + brend.getId() + "\" title=\"Delete\"><i class=\"la la-trash\"></i></a>";
            }
            BrendListDto  brendListDto = BrendListDto.builder()
                    .name(brend.getName())
                    .actions(actions)
                    .build();
            brendList.add(brendListDto);
        });
        long brendCount = brendRepository.countAllByActive(ACTIVE.ordinal());
        return createDataTable(brendList, brendCount, page, perPage);

    }

    public CommonResponse addBrend(BrendDto brendDto) throws JsonProcessingException {
        log.info("*****Add Brend Service*****");
        log.info(String.format("brendDto = %s", brendDto));

        BrendEntity brendEntity = BrendEntity.builder()
                .name(brendDto.getName())
                .active(ACTIVE.ordinal())
                .dataDate(LocalDateTime.now())
                .build();
        brendRepository.save(brendEntity);
        return success();
    }

    public CommonResponse getBrendById(Long id) {
        log.info("*****Get Brend By Id Service*****");
        log.info(String.format("id = %s", id));

        BrendEntity brendEntity = brendRepository.findById(id).get();

        BrendDto brendDto = BrendDto.builder()
                .id(brendEntity.getId())
                .name(brendEntity.getName())
                .build();
        return success(brendDto);
    }

    public CommonResponse editBrend(BrendDto brendDto) throws JsonProcessingException {
        log.info("*****Edit Brend Service*****");
        log.info(String.format("brendDto = %s", brendDto));

        BrendEntity brendEntity = brendRepository.findById(brendDto.getId()).get();
        brendEntity.setName(brendDto.getName());
        brendRepository.save(brendEntity);
        return success();
    }


    public CommonResponse deleteBrend(Long id) {
        log.info("*****Delete Brend Service*****");
        log.info(String.format("id = %s", id));

        BrendEntity brendEntity = brendRepository.findById(id).get();
        brendEntity.setActive(DEACTIVE.ordinal());
        brendRepository.save(brendEntity);
        return success();
    }
}
