package az.elvin.constructionAdmin.mapper;

import az.elvin.constructionAdmin.dto.UserDto;
import az.elvin.constructionAdmin.dto.UserListDto;
import az.elvin.constructionAdmin.entity.UserEntity;
import az.elvin.constructionAdmin.enums.RoleEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static az.elvin.constructionAdmin.enums.ActiveEnum.ACTIVE;
import static az.elvin.constructionAdmin.enums.ActiveEnum.DEACTIVE;
import static java.util.Objects.isNull;

@Mapper
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Mappings({
            @Mapping(target = "roleName", source = "role.name", qualifiedByName = "roleName"),
            @Mapping(target = "status", source = "active", qualifiedByName = "status"),
            @Mapping(target = "createdDate", source = "dataDate", qualifiedByName = "createdDate")
    })
    public abstract UserListDto entityToDto(UserEntity user);

    @Named("roleName")
    public static String roleName(String roleName) {
        if (isNull(roleName)) return null;
        if (roleName.equals(RoleEnum.ROLE_ADMIN.name())) return RoleEnum.ROLE_ADMIN.getRoleName();
        else if (roleName.equals(RoleEnum.ROLE_OPERATOR.name())) return RoleEnum.ROLE_OPERATOR.getRoleName();
        else if (roleName.equals(RoleEnum.ROLE_DOCTOR.name())) return RoleEnum.ROLE_DOCTOR.getRoleName();
        else if (roleName.equals(RoleEnum.ROLE_NURSE.name())) return RoleEnum.ROLE_NURSE.getRoleName();
        else if (roleName.equals(RoleEnum.ROLE_DRIVER.name())) return RoleEnum.ROLE_DRIVER.getRoleName();
        else return null;
    }

    @Named(value = "status")
    public static String status(Integer status) {
        if (status.equals(ACTIVE.ordinal()))
            return "active";
        else if (status.equals(DEACTIVE.ordinal()))
            return "deactive";
        return null;
    }

    @Named(value = "createdDate")
    public static String createdDate(LocalDateTime createdDate) {
        return dateTimeFormatter.format(createdDate);
    }

    @Mappings({
            @Mapping(target = "role.id", source = "roleId"),
            @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword"),
            @Mapping(target = "active", expression = "java(active())")
    })
    public abstract UserEntity dtoToEntity(UserDto userDto);

//    @Mappings({
//            @Mapping(target = "role.id", expression = "java(role())"),
//            @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword"),
//            @Mapping(target = "active", expression = "java(active())")
//    })
//    public abstract UserEntity dtoToEntity(DriverDto driverDto);


//    @Mappings({
//            @Mapping(target = "role.id", expression = "java(role())"),
//            @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword"),
//            @Mapping(target = "active", expression = "java(active())")
//    })
//    public abstract UserEntity dtoToEntity(NurseDto nurseDto);
//
//    @Mappings({
//            @Mapping(target = "role.id", expression = "java(role())"),
//            @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword"),
//            @Mapping(target = "active", expression = "java(active())")
//    })
//    public abstract UserEntity dtoToEntity(DoctorDto doctorDto);


    @Named(value = "encodePassword")
    public static String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public Integer active() {
        return ACTIVE.ordinal();
    }

    public Long role() {
        return (long) RoleEnum.ROLE_DRIVER.ordinal();
    }

    @Mapping(target = "roleId", source = "role.id")
    public abstract UserDto entityToDtoById(UserEntity userEntity);
}
