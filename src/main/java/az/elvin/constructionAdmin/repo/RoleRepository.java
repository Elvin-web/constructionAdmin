package az.elvin.constructionAdmin.repo;

import az.elvin.constructionAdmin.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = "select r from RoleEntity r where r.id <> 1")
    List<RoleEntity>findAll();
}
