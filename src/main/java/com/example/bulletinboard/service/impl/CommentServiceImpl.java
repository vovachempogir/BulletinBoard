package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.service.AdService;
import com.example.bulletinboard.service.CommentMapper;
import com.example.bulletinboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto create(CreateOrUpdateComment comment) {
        return commentMapper.toDto(commentRepo.save(commentMapper.updateToComment(comment)));
    }

    @Override
    public CommentDto getCommentsByAdId(Integer id) {
        return commentMapper.toDto(commentRepo.findById(id).orElseThrow(CommentNotFoundException::new));
    }
    @Override
    public List<CommentDto> getAll() {
        return commentRepo.findAll()
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Integer id) {
        if (commentRepo.existsById(id)) {
            commentRepo.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public void updateComment(Integer commentId , CreateOrUpdateComment comment) {
        commentRepo.findById(commentId).map(foundComment -> {
            foundComment.setText(comment.getText());
            return commentMapper.fromUpdateComment(commentRepo.save(foundComment));
        }).orElseThrow(CommentNotFoundException::new);
    }

    private class CommentNotFoundException extends RuntimeException {
        public CommentNotFoundException(String msg) {
            super(msg);
        }
        public CommentNotFoundException(String msg, Throwable cause) {
            super(msg, cause);
        }

        public CommentNotFoundException() {
            super("Комментарий не найден");
        }
    }
}
