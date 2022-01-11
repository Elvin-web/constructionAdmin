package az.elvin.constructionAdmin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private String mobile;

    private Integer roleId;

    private Long hospitalId;

    private MultipartFile image;
}
