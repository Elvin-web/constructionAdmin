package az.elvin.thread.v3.service;

import az.elvin.thread.dto.respone.CommonResponse;
import az.elvin.thread.entity.BrendEntity;
import az.elvin.thread.entity.CategoryEntity;
import az.elvin.thread.repo.BrendRepository;
import az.elvin.thread.repo.CategoryRepository;
import az.elvin.thread.v3.threadClass.ThreadV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThreadV3Service {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrendRepository brendRepository;

    public CommonResponse getThreadRepo3List() {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(1L);
        CategoryEntity category = categoryEntity.get();

        ThreadV3 threadV3 = new ThreadV3(brendRepository);
        try {
           BrendEntity brendEntity= threadV3.call();
           return CommonResponse.success(brendEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResponse.success(category);

    }

}
