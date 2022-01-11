package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findAllByActiveOrderByIdDesc(Integer active, Pageable pageable);

    long countAllByActive(Integer active);

    List<CategoryEntity> findAllByActive(Integer active);
}
