package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper  {

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
