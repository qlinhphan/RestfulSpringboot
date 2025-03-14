package vn.hoidanit.jobhunter.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;

@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {

    AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private ObjectMapper mapper;

    public CustomAuthenticationEntrypoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);

        RestResponse<Object> res = new RestResponse<Object>();
        res.setSttErr(HttpStatus.UNAUTHORIZED.value());

        String errMess = Optional.ofNullable(authException.getCause())
                .map(Throwable::getMessage).orElse(authException.getMessage());
        res.setMessage(errMess);
        res.setErr(
                "The token is invalid");

        mapper.writeValue(response.getWriter(), errMess);
    }

}
