package az.elvin.constructionAdmin.service;

import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.entity.UserEntity;
import az.elvin.constructionAdmin.enums.ResponseEnum;
import az.elvin.constructionAdmin.exception.CommonException;
import az.elvin.constructionAdmin.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public LoginService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public CommonResponse login(String username, String password) {
        log.info("*****Login Service*****");
        log.info(String.format("username = %s", username));
        UserEntity user = userRepository.findByUsername(username);
        if (!isNull(user) && encoder.matches(password, user.getPassword())) {
            log.info(String.format("Success operation! username = %s ", username));
            return CommonResponse.success(user);
        }
        throw new CommonException(ResponseEnum.USER_NOT_FOUND);
    }
}
