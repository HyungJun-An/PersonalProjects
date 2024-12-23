package com.project.community.repository;

import com.project.community.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsById(String id);

    boolean existsByNickname(String nickname);

    User findByEmail(String email);
}
