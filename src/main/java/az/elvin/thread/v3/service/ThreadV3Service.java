package az.elvin.thread.v3.service;

import az.elvin.thread.dto.respone.CommonResponse;
import az.elvin.thread.entity.CategoryEntity;
import az.elvin.thread.repo.BrendRepository;
import az.elvin.thread.repo.CategoryRepository;
import az.elvin.thread.v3.threadClass.ThreadV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThreadV3Service {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrendRepository brendRepository;

    public CommonResponse getThreadRepoList() {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(1L);
        CategoryEntity category = categoryEntity.get();

        ThreadV2 threadV2 = new ThreadV2(brendRepository);
        threadV2.run();
        return CommonResponse.success(category);

    }

}
