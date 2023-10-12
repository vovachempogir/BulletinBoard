package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.service.AdService;
import com.example.bulletinboard.service.CommentMapper;
import com.example.bulletinboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final AdService adService;

    @Override
    public CommentDto create(Integer id, CommentDto comment) {
        return commentMapper.toDto(commentRepo.save(commentMapper.toComment(comment)));
    }

    @Override
    public Optional<CommentDto> getCommentsByAdId(Integer id) {
        return Optional.ofNullable(commentMapper.toDto(commentRepo.findById(id).
                orElse(null)));
    }
    @Override
    public List<CommentDto> getAll() {
        return commentRepo.findAll()
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        Comment comment = commentRepo.getById(id);
        commentRepo.delete(comment);
    }
    @Override
    public void updateComment(Integer commentId , CreateOrUpdateComment comment) {
        AtomicReference<Optional<CreateOrUpdateComment>> atomicReference = new AtomicReference<>();
        commentRepo.findById(commentId).ifPresentOrElse(foundComment -> {
            foundComment.setText(comment.getText());
            atomicReference.set(Optional.of(commentMapper.fromUpdateComment(commentRepo.save(foundComment))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
    }
}
