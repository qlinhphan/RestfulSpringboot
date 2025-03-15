package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.AccessToken;
import vn.hoidanit.jobhunter.domain.dto.AddInforForAccessToken;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;
import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.anotation.AnotationRes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final CompanyController companyController;

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private SecurityUtil securityUtil;
    private UserService userService;

    @Value("${hoidanit.jwt.access-token-validity-in-seconds}")
    private long jwtExpirationRefresToken;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            UserService userService, CompanyController companyController) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.companyController = companyController;
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
        if (us != null) {
            AddInforForAccessToken aff = new AddInforForAccessToken();
            aff.setId(us.getId());
            aff.setName(us.getName());
            aff.setEmail(us.getEmail());
            at.setAddInforForAccessToken(aff);
        }

        String refresh_token = this.securityUtil.createRefreshToken(loginDTO.getUsername(), at);

        this.userService.updateRefreshTokenForUser(refresh_token, loginDTO.getUsername());

        ResponseCookie springCookie = ResponseCookie.from("user", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtExpirationRefresToken)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .build();
    }

    @AnotationRes("api logout")
    @PostMapping("/logout")
    public ResponseEntity<RestResponse> postMethodName() {
        String email = this.securityUtil.getCurrentUserLogin().get();

        User us = this.userService.findUserByEmail(email);

        this.userService.updateRefreshTokenForUser(null, email);

        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("user", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .build();

    }

}
