package vn.hoidanit.jobhunter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.controller.AuthController;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.AccessToken;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User us) {
        return this.userRepository.save(us);
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User findUserById(long id) {
        return this.userRepository.findById(id);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public boolean checkEmailIsExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public List<User> findAllUsersNoFilterNoPagi() {
        return this.userRepository.findAll();
    }

    public Page<User> findAllUsersButFilterPagi(Specification<User> spe, Pageable pageable) {
        return this.userRepository.findAll(spe, pageable);
    }

    public void updateRefreshTokenForUser(String refreshToken, String email) {
        User us = this.findUserByEmail(email);
        us.setRefreshToken(refreshToken);
        this.saveUser(us);
    }
}
