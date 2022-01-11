package az.cc103.doctorNurseDriver.security;

import az.cc103.doctorNurseDriver.entity.UserEntity;
import az.cc103.doctorNurseDriver.enums.ResponseEnum;
import az.cc103.doctorNurseDriver.exception.UserNotFoundException;
import az.cc103.doctorNurseDriver.repo.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(username))
                .orElseThrow(() -> new UserNotFoundException(ResponseEnum.USER_NOT_FOUND));
        return new User(userEntity.getUsername(), userEntity.getPassword(),
                AuthorityUtils.createAuthorityList(userEntity.getRole().getName()));
    }
}
