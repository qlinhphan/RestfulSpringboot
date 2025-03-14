package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.AccessToken;
import vn.hoidanit.jobhunter.domain.dto.AddInforForToken;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;
import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.anotation.AnotationRes;

import java.net.http.HttpHeaders;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private SecurityUtil securityUtil;
    private UserService userService;

    @Value("${hoidanit.jwt.access-token-validity-in-seconds}")
    private long jwtExpirationRefresToken;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    @AnotationRes("Api login")
    public ResponseEntity<AccessToken> postLogin(@Valid @RequestBody LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xac thuc nguoi dung, can viet ham loaduserbyusername
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // create a token (neu nguoi dung dang nhap thanh cong spring secu se ko luu
        // thong tin nguoi dung)

        String token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccessToken at = new AccessToken();
        at.setToken(token);
        User us = this.userService.findUserByEmail(loginDTO.getUsername());
        AddInforForToken add = new AddInforForToken();
        if (us != null) {

            add.setId(us.getId());
            add.setName(us.getName());
            add.setEmail(us.getEmail());
            at.setAddInforForToken(add);
        }

        String refreshToken = this.securityUtil.createRefreshToken(loginDTO.getUsername(), at);

        this.userService.updateRefreshTokenForUser(loginDTO.getUsername(), refreshToken);

        ResponseCookie springCookie = ResponseCookie.from("user", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtExpirationRefresToken)
                .build();

        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.SET_COOKIE, springCookie.toString())
                .build();

    }

    @PostMapping("/log")
    public ResponseEntity<String> postMethodName() {
        // TODO: process POST request

        return ResponseEntity.ok("ok");
    }

}
