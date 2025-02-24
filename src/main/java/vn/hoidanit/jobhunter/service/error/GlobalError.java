package vn.hoidanit.jobhunter.service.error;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;

@RestControllerAdvice
public class GlobalError {

    @ExceptionHandler(value = IdInvalidService.class)
    public ResponseEntity<Object> handleBlogAlreadyExistsException(IdInvalidService invalidService) {

        RestResponse<Object> rest = new RestResponse<Object>();
        rest.setSttErr(HttpStatus.BAD_REQUEST.value());
        rest.setMessage("not this user !!");
        rest.setErr("have some Err at somewhere");
        rest.setData("no-data");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(rest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult rs = ex.getBindingResult();
        List<FieldError> fieldErrors = rs.getFieldErrors();

        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setSttErr(HttpStatus.BAD_REQUEST.value());
        restResponse.setErr(ex.getBody().getDetail());

        List<String> errs = new ArrayList<String>();
        for (FieldError f : fieldErrors) {
            errs.add(f.getDefaultMessage());
        }

        restResponse.setMessage(errs.size() > 1 ? errs : errs.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(restResponse);
    }

}
