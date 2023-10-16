package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto create(CreateOrUpdateComment comment);
    CommentDto getCommentsByAdId(Integer id);
    boolean delete(Integer id);
    List<CommentDto> getAll();
    void updateComment(Integer userId, CreateOrUpdateComment comment);

}
