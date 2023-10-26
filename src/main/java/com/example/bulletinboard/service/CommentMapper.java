package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.CommentDto;
import com.example.bulletinboard.dto.Comments;
import com.example.bulletinboard.dto.CreateOrUpdateComment;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", imports = Instant.class)
public interface CommentMapper  {

    default Comments to(List<Comment> results) {
        return to(results.size(),results);
    }

    Comments to(Integer count, List<Comment> results);

    @Mapping(target = "author", source = "user", qualifiedByName = "authorToInteger")
    @Mapping(target = "authorFirstName", source = "user", qualifiedByName = "authorFirstNameFromAuthor")
    @Mapping(target = "authorImage", source = "user", qualifiedByName = "authorImageToString")
    CommentDto toDto(Comment comment);

    @Mapping(target = "createdAt", expression = "java(Instant.ofEpochMilli(Instant.now().toEpochMilli()))")
    Comment updateToComment(CreateOrUpdateComment createOrUpdateComment);

    @Named("authorImageToString")
    default String authorImageToString(User user) {
        if (user.getImage() == null) {
            return null;
        }
        return "/users/image/" + user.getImage().getId();
    }

    @Named("authorToInteger")
    default Integer authorToInteger(User user) {
        return user.getId();
    }

    @Named("authorFirstNameFromAuthor")
    default String authorFirstNameFromAuthor(User author) {
        return author.getFirstName();
    }

}
