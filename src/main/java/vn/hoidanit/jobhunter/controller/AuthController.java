package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> postLogin(@Valid @RequestBody LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xac thuc nguoi dung, can viet ham loaduserbyusername
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        return ResponseEntity.ok(loginDTO);
    }

}
