package vn.hoidanit.jobhunter.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;

@RestControllerAdvice
public class ResponseData implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int stt = servletResponse.getStatus();
        System.out.println("INPUT ID: " + stt);
        if (stt >= 400) {
            return body;
        } else {
            RestResponse<Object> rest = new RestResponse<Object>();
            rest.setSttErr(stt);
            rest.setMessage("Data are called success");
            rest.setErr("No-err");
            rest.setData(body);

            return rest;
        }
    }

}
