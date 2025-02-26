package vn.hoidanit.jobhunter.service.error;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException ex) {

        BindingResult rs = ex.getBindingResult();
        List<FieldError> er = rs.getFieldErrors();

        RestResponse<Object> rr = new RestResponse<Object>();
        rr.setErr(ex.getBody().getDetail());
        rr.setSttErr(HttpStatus.BAD_REQUEST.value());

        List<String> errors = new ArrayList<>();
        for (FieldError e : er) {
            errors.add(e.getDefaultMessage());
        }

        rr.setMessage(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(rr);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<RestResponse<Object>> handleBadcridential(BadCredentialsException ex) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setSttErr(HttpStatus.BAD_REQUEST.value());
        res.setErr("have some errors");
        res.setMessage("Email or Password is not true");
        return ResponseEntity.badRequest().body(res);

    }

}
