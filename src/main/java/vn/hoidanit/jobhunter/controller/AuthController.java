package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.dto.AccessToken;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;
import vn.hoidanit.jobhunter.domain.formResponse.RestResponse;
import vn.hoidanit.jobhunter.util.SecurityUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> postLogin(@Valid @RequestBody LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xac thuc nguoi dung, can viet ham loaduserbyusername
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // create a token (neu nguoi dung dang nhap thanh cong spring secu se ko luu
        // thong tin nguoi dung)

        String token = this.securityUtil.createToken(authentication);
        AccessToken at = new AccessToken();
        at.setToken(token);

        return ResponseEntity.ok(at);

    }

}
