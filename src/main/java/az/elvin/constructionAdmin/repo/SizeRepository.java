package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.SizeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {

    Page<SizeEntity> findAllByActiveOrderByNameAsc(Integer active, Pageable pageable);

    long countAllByActive(Integer active);

    List<SizeEntity> findAllByActive(Integer active);
}
