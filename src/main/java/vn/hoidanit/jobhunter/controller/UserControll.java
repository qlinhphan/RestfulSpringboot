package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidExceptionService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserControll(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<User> createUser(@RequestBody User postmanUser) {
        User user = new User();
        user.setName(postmanUser.getName());
        user.setPassword(passwordEncoder.encode(postmanUser.getPassword()));
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
    public ResponseEntity<User> findUserById(@PathVariable("id") long id) throws IdInvalidExceptionService {
        if (id > 1500) {
            throw new IdInvalidExceptionService("KHONG DUOC TRUYEN ID LON HON 1500");
        }
        User user = this.userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchAllUser(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "name", defaultValue = "") String name) {
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<User> user = this.userService.findAllUser(name, pageable);
        List<User> users = user.getContent();
        System.out.println(page);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User userPostman) {
        User us = this.userService.findUserById(userPostman.getId());
        us = this.userService.saveUser(userPostman);
        return ResponseEntity.ok(us);
    }

}
