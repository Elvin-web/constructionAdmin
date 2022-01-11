package az.elvin.constructionAdmin.exception;

import az.elvin.constructionAdmin.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CommonException extends RuntimeException {

    private ResponseEnum responseEnum;
}
