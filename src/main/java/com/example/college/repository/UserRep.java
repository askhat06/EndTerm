package com.example.college.repository;
import com.example.college.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<UserModel, Long>{
    UserModel findByEmail(String email);
}
