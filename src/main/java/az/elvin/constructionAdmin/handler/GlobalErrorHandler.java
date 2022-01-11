package az.elvin.constructionAdmin.handler;

import az.elvin.constructionAdmin.dto.respone.CommonResponse;
import az.elvin.constructionAdmin.enums.ResponseEnum;
import az.elvin.constructionAdmin.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse commonException(Exception exception){
        log.error(exception.getMessage());
        exception.printStackTrace();
        return CommonResponse.builder()
                .statusCode(ResponseEnum.UNKNOWN_ERROR.getStatusCode())
                .statusMessage(ResponseEnum.UNKNOWN_ERROR.getStatusMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse commonException(CommonException commonException){
        log.error(commonException.getResponseEnum().getStatusMessage());
        return CommonResponse.builder()
                .statusCode(commonException.getResponseEnum().getStatusCode())
                .statusMessage(commonException.getResponseEnum().getStatusMessage())
                .data(null)
                .build();
    }
}
