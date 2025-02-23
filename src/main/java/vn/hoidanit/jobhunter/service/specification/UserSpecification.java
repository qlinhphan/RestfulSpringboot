package vn.hoidanit.jobhunter.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;

@Service
public class UserSpecification {

    public Specification<User> likeNameUser(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }
}
