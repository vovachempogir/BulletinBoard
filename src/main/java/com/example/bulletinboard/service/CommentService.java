package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto create(Integer adID, CreateOrUpdateComment comment);
    CommentDto getCommentsByAdId(Integer id);
    Comments getAll();
    void delete(Integer adId, Integer commentId);
    void updateComment(Integer userId, CreateOrUpdateComment comment);

}
