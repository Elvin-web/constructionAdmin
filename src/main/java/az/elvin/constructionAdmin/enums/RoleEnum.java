package az.elvin.constructionAdmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {

    NONE("none"),
    ROLE_SUPER_ADMIN("Super admin"),
    ROLE_ADMIN("Admin"),
    ROLE_OPERATOR("Operator"),
    ROLE_DOCTOR("Həkim"),
    ROLE_NURSE("Tibb bacısı"),
    ROLE_DRIVER("Sürücü");

    private String roleName;
}
