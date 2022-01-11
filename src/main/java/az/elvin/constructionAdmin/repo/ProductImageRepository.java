package az.elvin.constructionAdmin.repo;


import az.elvin.constructionAdmin.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

    ProductImageEntity findProductImageEntityByProductId(Long productId);

}
