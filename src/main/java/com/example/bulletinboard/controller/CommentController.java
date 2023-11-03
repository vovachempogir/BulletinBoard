package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.Comments;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.service.CommentService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для работы с комментариями.
 */
@Api(tags = "Комментарии")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;

    /**
     * Создает новый комментарий к объявлению.
     * @param id идентификатор объявления
     * @param comment информация о комментарии
     * @return DTO с созданным комментарием
     */
    @PostMapping("/{id}/comments")
    public CommentDto create(@PathVariable Integer id, @RequestBody CreateOrUpdateComment comment) {
        return commentService.create(id, comment);
    }

    /**
     * Получает все комментарии по идентификатору объявления.
     * @param id идентификатор объявления
     * @return объект с списком комментариев
     */
    @GetMapping("/{id}/comments")
    public Comments getCommentsByAdId(@PathVariable Integer id) {
        return commentService.getAll(id);
    }

    /**
     * Удаляет комментарий по идентификатору объявления и комментария.
     * @param adId идентификатор объявления
     * @param commentId идентификатор комментария
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteById(@PathVariable Integer adId, @PathVariable Integer commentId) {
        commentService.delete(adId, commentId);
    }

    /**
     * Обновляет информацию о комментарии по идентификатору объявления и комментария.
     * @param adId идентификатор объявления
     * @param commentId идентификатор комментария
     * @param comment информация о комментарии
     * @return DTO с обновленным комментарием
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    public CommentDto update(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CreateOrUpdateComment comment) {
        return commentService.updateComment(adId, commentId, comment);
    }
}