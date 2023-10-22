package com.example.bulletinboard.repository;

import com.example.bulletinboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    Comment deleteCommentByIdAndAd_Id(Integer adId, Integer commentId);

    Comment deleteAllByAd_Id(Integer id);

    List<Comment> findAllByAd_Id(Integer adId);
}
