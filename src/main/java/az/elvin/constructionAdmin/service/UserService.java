package az.elvin.constructionAdmin.service;

import az.elvin.constructionAdmin.dto.RoleDto;
import az.elvin.constructionAdmin.dto.UserDto;
import az.elvin.constructionAdmin.dto.UserListDto;
import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.dto.respone.Description;
import az.elvin.constructionAdmin.entity.RoleEntity;
import az.elvin.constructionAdmin.entity.UserEntity;
import az.elvin.constructionAdmin.enums.ActiveEnum;
import az.elvin.constructionAdmin.enums.ResponseEnum;
import az.elvin.constructionAdmin.exception.CommonException;
import az.elvin.constructionAdmin.mapper.UserMapper;
import az.elvin.constructionAdmin.repo.RoleRepository;
import az.elvin.constructionAdmin.repo.UserRepository;
import az.elvin.constructionAdmin.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static az.elvin.constructionAdmin.dto.respone.CommonResponse.success;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ObjectMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> getUsers(int page, String perPage) {
        log.info("*****Get User List Service*****");
        List<UserListDto> userList = new LinkedList<>();

        Page<UserEntity> getUsers = userRepository.getUsers(PageRequest.of(page - 1, CommonUtil.getPerPage(perPage)));
        getUsers.forEach(user -> {
            String actions = "";
            if (user.getActive().equals(ActiveEnum.ACTIVE.ordinal())) {
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill userEdit\" data-id=\"" + user.getId() + "\" title=\"Edit\"><i class=\"la la-edit\"></i></a>";
                actions += "<a href=\"#\" class=\" m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill userDelete\" data-id=\"" + user.getId() + "\" title=\"Delete\"><i class=\"la la-trash\"></i></a>";
            }
            UserListDto userListDto = UserMapper.INSTANCE.entityToDto(user);
            userListDto.setActions(actions);
            userList.add(userListDto);
        });
        long userCount = userRepository.getUsersCount();
        return CommonUtil.createDataTable(userList, userCount, page, perPage);
    }

    public CommonResponse addUser(UserDto userDto) throws IOException {
        log.info("*****Add User Service*****");
        log.info(String.format("userDto = %s", userDto));

        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername());
        if (!isNull(userEntity)) throw new CommonException(ResponseEnum.USERNAME_IS_EXIST);

        userEntity = UserMapper.INSTANCE.dtoToEntity(userDto);

        userRepository.save(userEntity);
        return success();
    }

    public CommonResponse getUserById(Long id) {
        log.info("*****Get User By Id Service*****");
        log.info(String.format("id = %s", id));

        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) throw new CommonException(ResponseEnum.USER_NOT_FOUND);
        UserDto userDto = UserMapper.INSTANCE.entityToDtoById(userEntity.get());
        return success(userDto);
    }

    public CommonResponse editUser(Long id, UserDto userDto) throws IOException {
        log.info("*****Edit User Service*****");
        log.info(String.format("userDto = %s", userDto));

        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) throw new CommonException(ResponseEnum.USER_NOT_FOUND);

        UserEntity user = userEntity.get();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setMobile(userDto.getMobile());
        RoleEntity role = new RoleEntity();
        role.setId((long) userDto.getRoleId());
        user.setRole(role);
//        user.setHospitalId(userDto.getHospitalId());
//        user.setUpdateDate(LocalDateTime.now());
//        user.setProfileImage(userDto.getImage() != null ? userDto.getImage().getBytes() : user.getProfileImage());
        userRepository.save(user);
        return success();
    }

    public CommonResponse deleteUser(Long id) {
        log.info("*****Delete User Service*****");
        log.info(String.format("id = %s", id));

        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) throw new CommonException(ResponseEnum.USER_NOT_FOUND);
        UserEntity user = userEntity.get();
        user.setActive(ActiveEnum.DEACTIVE.ordinal());
        userRepository.save(user);
        return success();
    }

    public CommonResponse profile() {
        log.info("*****Get Profile Service*****");
        UserDto userDto = UserMapper.INSTANCE.entityToDtoById(getCurrentUser());
        return success(userDto);
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

    public CommonResponse getRoleList() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleDto> roleDtos = new LinkedList<>();
        roleEntities.forEach(roleEntity -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(roleEntity.getId());
            try {
                Description description = mapper.readValue(roleEntity.getDescription(), Description.class);
                roleDto.setName(description.getAz());
            } catch (JsonProcessingException e) {
            }
            roleDtos.add(roleDto);
        });
        return success(roleDtos);
    }
}
