package com.example.bulletinboard.repository;

import com.example.bulletinboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}
