package com.example.bulletinboard.repository;

import com.example.bulletinboard.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Integer> {
}
