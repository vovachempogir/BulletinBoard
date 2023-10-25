package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.*;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.entity.User;
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
    public Comments getAll(Integer adId) {
        log.info("getAllComments");
        return commentMapper.to(commentRepo.findAllByAd_Id(adId));
    }

    @Override
    public void delete(Integer adId, Integer commentId) {
        User user = getUser();
        Ad ad = getAd(adId);
        Comment comment = commentRepo.deleteCommentByIdAndAd_Id(adId, commentId);
        if (rightsVerification(user, ad)) {
            commentRepo.delete(comment);
        } else {
            throw new UnsupportedOperationException("Нет прав на удаление комментария");
        }
    }

    @Override
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateComment updateComment) {
        User user = getUser();
        Ad ad = getAd(adId);
        if (rightsVerification(user, ad)) {
            Comment comment = commentRepo.findByIdAndAd_Id(commentId, adId);
            return commentMapper.toDto(commentRepo.save(comment));
        } else {
            throw new UnsupportedOperationException("Нет прав на изменение комментария");
        }
    }

    private Ad getAd(Integer id) {
        return adRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Объявление не найдено")) ;
    }

    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    private boolean rightsVerification(User user, Ad ad) {
        return (user.getRole().equals(Role.ADMIN) || ad.getUser().equals(user));
    }
}
