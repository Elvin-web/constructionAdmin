package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAllByActiveOrderByNameAsc(Integer active, Pageable pageable);

    long countAllByActive(Integer active);
}
