package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultOfPagination;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidService;
import vn.hoidanit.jobhunter.util.anotation.AnotationRes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserControll {

    private final AuthController authController;

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserControll(UserService userService, PasswordEncoder passwordEncoder, AuthController authController) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authController = authController;
    }

    // @GetMapping("/create-user")
    // public String createUser() {
    // User user = new User();
    // user.setName("Linh");
    // user.setEmail("qlinhphan@gmail.com");
    // user.setPassword("Linhlinh04@");
    // this.userService.saveUser(user);
    // return "create-user";
    // }

    @PostMapping("/users")
    @AnotationRes(value = "api add user")
    public ResponseEntity<Object> createUser(@RequestBody User postmanUser) {
        User user = new User();
        user.setName(postmanUser.getName());
        user.setPassword(passwordEncoder.encode(postmanUser.getPassword()));
        if (this.userService.checkEmailIsExist(postmanUser.getEmail())) {
            return ResponseEntity.badRequest().body("A User with this email is existed !!");
        }
        user.setEmail(postmanUser.getEmail());
        User needSave = this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(needSave);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted!!");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") long id) throws IdInvalidService {

        if (id > 1500) {
            throw new IdInvalidService("Can not transfer this Id, because it is greather than 1500");
        }

        User user = this.userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    @AnotationRes(value = "select all users")
    public ResponseEntity<ResultOfPagination> fetchAllUser(@Filter Specification<User> spec,
            @RequestParam(value = "page", defaultValue = "1") String page) {
        int p = Integer.parseInt(page);
        Pageable pageable = PageRequest.of(p - 1, 3);
        Page<User> users = userService.findAllUsersButFilterPagi(spec, pageable);
        List<User> us = users.getContent();

        Meta meta = new Meta();
        meta.setCurrenPage(p);
        meta.setPageSize(3);
        meta.setTotalElement(users.getTotalElements());
        meta.setTotalPage(users.getTotalPages());

        ResultOfPagination rp = new ResultOfPagination();
        rp.setMeta(meta);
        rp.setResultOfPagina(us);

        return ResponseEntity.status(HttpStatus.OK).body(rp);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User userPostman) {
        User us = this.userService.findUserById(userPostman.getId());
        us = this.userService.saveUser(userPostman);
        return ResponseEntity.ok(us);
    }

}
