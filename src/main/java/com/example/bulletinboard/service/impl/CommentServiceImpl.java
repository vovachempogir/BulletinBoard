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

/**
 * Реализация интерфейса CommentService, предоставляющая функциональность по  созданию, поиску, изменению, удалению комментариев.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final AdRepo adRepo;
    private final UserRepo userRepo;
    private final UserDetails userDetails;

    /**
     * Создает новый комментарий для заданного объявления и сохраняет его в базе данных.
     *
     * @param adId идентификатор объявления, для которого создается комментарий
     * @param createComment объект CreateOrUpdateComment с данными для создания комментария
     * @return объект CommentDto, представляющий созданный комментарий
     */
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

    /**
     * Возвращает список всех комментариев для заданного объявления.
     *
     * @param adId идентификатор объявления
     * @return объект Comments, содержащий список всех комментариев для заданного объявления
     */
    @Override
    @Transactional
    public Comments getAll(Integer adId) {
        log.info("getAllComments");
        return commentMapper.to(commentRepo.findAllByAd_Id(adId));
    }

    /**
     * Удаляет комментарий по заданному идентификатору для указанного объявления.
     *
     * @param adId идентификатор объявления, к которому относится комментарий
     * @param commentId идентификатор комментария для удаления
     * @throws ForbiddenException если у пользователя нет прав на удаление комментария
     * @throws NoSuchElementException если комментарий не найден
     */
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

    /**
     * Обновляет информацию о комментарии для заданного объявления.
     *
     * @param adId идентификатор объявления, к которому относится комментарий
     * @param commentId идентификатор комментария
     * @param updateComment объект CreateOrUpdateComment с обновленными данными комментария
     * @return объект CommentDto, представляющий обновленный комментарий
     * @throws ForbiddenException если у пользователя нет прав на изменение комментария
     * @throws NoSuchElementException если объявление или комментарий не найдены
     */
    @Override
    @Transactional
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateComment updateComment) {
        User user = getUser();
        Comment comment = getComment(commentId);
        Ad ad = getAd(adId);
        if (rightsVerification(user, comment)) {
            comment.setText(updateComment.getText());
            log.info("updateComment");
            return commentMapper.toDto(commentRepo.save(comment));
        } else {
            log.info("notUpdateComment");
            throw new ForbiddenException("Нет прав на изменение комментария");
        }
    }

    /**
     * Возвращает объявление по заданному идентификатору.
     *
     * @param id идентификатор объявления
     * @return объект Ad, представляющий найденное объявление
     * @throws NoSuchElementException если объявление не найдено
     */
    private Ad getAd(Integer id) {
        return adRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Объявление не найдено")) ;
    }

    /**
     * Возвращает объект пользователя на основе информации о текущем пользователе.
     *
     * @return объект User, представляющий текущего пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Возвращает комментарий по заданному идентификатору.
     *
     * @param id идентификатор комментария
     * @return объект Comment, представляющий найденный комментарий
     * @throws NoSuchElementException если комментарий не найден
     */
    private Comment getComment(Integer id) {
        return commentRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    /**
     * Проверяет соответствие прав пользователя для данного комментария.
     *
     * @param user пользователь для проверки прав
     * @param comment комментарий для проверки прав
     * @return true, если у пользователя есть права на данный комментарий, иначе false
     */
    private boolean rightsVerification(User user, Comment comment) {
        return (user.getRole().equals(Role.ADMIN) || comment.getUser().equals(comment.getUser()));
    }
}
