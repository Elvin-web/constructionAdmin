package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.ColourEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColourRepository extends JpaRepository<ColourEntity, Long> {

    Page<ColourEntity> findAllByActiveOrderByIdDesc(Integer active, Pageable pageable);

    long countAllByActive(Integer active);

    List<ColourEntity> findAllByActiveOrderById(Integer active);
}
