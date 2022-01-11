package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.BrendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrendRepository extends JpaRepository<BrendEntity, Long> {

    Page<BrendEntity> findAllByActiveOrderByNameAsc(Integer active, Pageable pageable);

    long countAllByActive(Integer active);

    List<BrendEntity> findAllByActiveOrderById(Integer active);
}
