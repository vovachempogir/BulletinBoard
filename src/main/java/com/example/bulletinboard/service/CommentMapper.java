package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.Comments;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper  {

    default Comments to(List<Comment> results) {
        return to(results.get(0).getId(),results);
    }

    Comments to(Integer count, List<Comment> results);

    @Mapping(source = "user.id",target = "author")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "ad.id", target = "pk")
    CommentDto toDto(Comment comment);

    CreateOrUpdateComment fromUpdateComment(Comment comment);

    CommentDto toComment(CreateOrUpdateComment createOrUpdateComment);

    Comment updateToComment(CreateOrUpdateComment createOrUpdateComment);

    List<CommentDto> commentsToCommentsDto(List<Comment> comments);
}
