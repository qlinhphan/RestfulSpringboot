package vn.hoidanit.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.service.error.IdInvalidService;

@RestController
public class HelloController {

    @GetMapping("/")
    public String getHelloWorld() throws IdInvalidService {
        if (true) {
            throw new IdInvalidService("this is for catching err");
        }
        return "Hello World (Hỏi Dân IT & Eric)";
    }
}
