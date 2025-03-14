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
import vn.hoidanit.jobhunter.util.anotation.AnotationRes;

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
        if (body instanceof String) {
            return body;
        }
        if (stt >= 400) {
            return body;
        } else {
            RestResponse<Object> rest = new RestResponse<Object>();
            rest.setSttErr(stt);

            AnotationRes mess = returnType.getMethodAnnotation(AnotationRes.class);

            rest.setMessage(mess.value());
            rest.setErr("No-err");
            rest.setData(body);
            return rest;
        }
    }

}
