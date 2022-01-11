package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Page<CountryEntity> findAllByActiveOrderByIdDesc(Integer active, Pageable pageable);

    long countAllByActive(Integer active);

    List<CountryEntity> findAllByActive(Integer active);
}
