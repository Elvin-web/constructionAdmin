package az.elvin.constructionAdmin.repo;

import az.elvin.constructionAdmin.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    @Query(value = "select u from UserEntity u where (u.role.id = 2 or u.role.id = 3) and u.active = 1 order by u.id desc")
    Page<UserEntity> getUsers(Pageable pageable);

    @Query(value = "select count(u.id) from UserEntity u where (u.role.id = 2 or u.role.id = 3) and u.active = 1")
    long getUsersCount();
}
