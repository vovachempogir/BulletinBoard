package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.exception.ForbiddenException;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.CommentMapper;
import com.example.bulletinboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final AdRepo adRepo;
    private final UserRepo userRepo;
    private final UserDetails userDetails;

    @Override
    @Transactional
    public CommentDto create(Integer adId, CreateOrUpdateComment createComment) {
        Ad ad = getAd(adId);
        User user = getUser();
        Comment comment = commentMapper.updateToComment(createComment);
        comment.setUser(user);
        comment.setAd(ad);
        commentRepo.save(comment);
        log.info("createComment");
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public Comments getAll(Integer adId) {
        log.info("getAllComments");
        return commentMapper.to(commentRepo.findAllByAd_Id(adId));
    }

    @Override
    @Transactional
    public void delete(Integer adId, Integer commentId) {
        User user = getUser();
        Comment comment = getComment(commentId);
        if (rightsVerification(user, comment)) {
            commentRepo.delete(comment);
            log.info("deleteComment");
        } else {
            log.info("notDeleteComment");
            throw new ForbiddenException("Нет прав на удаление комментария");
        }
    }

    @Override
    @Transactional
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateComment updateComment) {
        User user = getUser();
        Comment comment = getComment(commentId);
        Ad ad = getAd(adId);
        if (rightsVerification(user, comment)) {
            comment.setText(updateComment.getText());
            comment.setCreatedAt(Instant.ofEpochMilli(Instant.now().toEpochMilli()));
            comment.setAd(ad);
            comment.setUser(user);
            log.info("updateComment");
            return commentMapper.toDto(commentRepo.save(comment));
        } else {
            log.info("notUpdateComment");
            throw new ForbiddenException("Нет прав на изменение комментария");
        }
    }

    private Ad getAd(Integer id) {
        return adRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Объявление не найдено")) ;
    }

    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    private Comment getComment(Integer id) {
        return commentRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    private boolean rightsVerification(User user, Comment comment) {
        return (user.getRole().equals(Role.ADMIN) || comment.getUser().equals(comment.getUser()));
    }
}
