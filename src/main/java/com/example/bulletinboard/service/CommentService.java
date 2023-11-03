package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto create(Integer adID, CreateOrUpdateComment comment);
    Comments getAll(Integer id);
    void delete(Integer adId, Integer commentId);
    CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateComment updateComment);

}
