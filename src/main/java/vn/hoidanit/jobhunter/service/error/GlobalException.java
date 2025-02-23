package vn.hoidanit.jobhunter.service.error;

import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = IdInvalidExceptionService.class)
    public ResponseEntity<RestResponse> handleBlogAlreadyExistsException(
            IdInvalidExceptionService idInvalidExceptionService) {
        RestResponse<Object> rest = new RestResponse<Object>();
        rest.setSttErr(HttpStatus.BAD_REQUEST.value());
        rest.setMessage("can not call api");
        rest.setErr("have some err");
        rest.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(rest);
    }
}
