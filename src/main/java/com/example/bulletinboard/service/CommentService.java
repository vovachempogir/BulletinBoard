package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto create(Integer id, CommentDto comment);
    Optional<CommentDto> getCommentsByAdId(Integer id);
    void delete(Integer id);
    List<CommentDto> getAll();
    public void updateComment(Integer userId, CreateOrUpdateComment comment);

}
