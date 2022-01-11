package az.cc103.doctorNurseDriver.repo;

import az.cc103.doctorNurseDriver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameIgnoreCase(String username);
}
