package vn.hoidanit.jobhunter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.specification.UserSpecification;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserSpecification userSpecification;

    public UserService(UserRepository userRepository, UserSpecification userSpecification) {
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
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

    public Page<User> findAllUser(String name, Pageable pageable) {
        Specification<User> spec = this.userSpecification.likeNameUser(name);
        return this.userRepository.findAll(spec, pageable);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
