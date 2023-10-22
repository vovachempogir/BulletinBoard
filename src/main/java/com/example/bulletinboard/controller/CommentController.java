package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.Comments;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Integer id,
                                             @RequestBody CreateOrUpdateComment comment) {
        CommentDto commentDto = commentService.create(id, comment);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/{id}/comments")
    public Comments getCommentsByAdId(@PathVariable Integer id) {
        return commentService.getAll(id);
    }
    

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Integer adId,
                                       @PathVariable Integer commentId) {
        commentService.delete(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateComment> update(@PathVariable Integer commentId, @RequestBody CreateOrUpdateComment comment) {
        commentService.updateComment(commentId, comment);
        return ResponseEntity.ok().build();
    }



}