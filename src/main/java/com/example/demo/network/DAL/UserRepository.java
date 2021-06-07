package com.example.demo.network.DAL;

import com.example.demo.network.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findById(Long id);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
//    Optional<UserModel> findUserByEmail(String email);
}
