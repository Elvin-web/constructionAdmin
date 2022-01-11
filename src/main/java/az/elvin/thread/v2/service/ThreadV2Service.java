package az.elvin.constructionAdmin.v2.service;

import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.entity.CategoryEntity;
import az.elvin.constructionAdmin.repo.CategoryRepository;
import az.elvin.constructionAdmin.v2.threadClass.ThreadV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThreadV2Service {

    @Autowired
    CategoryRepository categoryRepository;

    public CommonResponse getThreadRepoList() {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(1L);
        CategoryEntity category = categoryEntity.get();

        ThreadV2 threadV2 = new ThreadV2();
        threadV2.run();
        return CommonResponse.success(category);

    }

}
