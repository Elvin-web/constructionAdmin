package az.elvin.constructionAdmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(1, "Success operation"),
    USER_NOT_FOUND(2, "User not found"),
    USERNAME_IS_EXIST(3, "Username is exist"),
    PRODUCT_NOT_FOUND(4, "Product not found"),
    UNKNOWN_ERROR(500, "Unknown error has occured");

    private final Integer statusCode;

    private final String statusMessage;

}
