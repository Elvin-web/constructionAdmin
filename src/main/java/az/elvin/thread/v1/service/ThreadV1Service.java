package az.elvin.thread.v1.service;

import az.elvin.thread.dto.respone.CommonResponse;
import az.elvin.thread.entity.CategoryEntity;
import az.elvin.thread.repo.CategoryRepository;
import az.elvin.thread.v1.threadClass.MainThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThreadService {

    @Autowired
    CategoryRepository categoryRepository;

    public CommonResponse getThreadRepoList() {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(1L);
        CategoryEntity category = categoryEntity.get();

        MainThread mainThread = new MainThread();
        mainThread.start();

        return CommonResponse.success(category);

    }

}
