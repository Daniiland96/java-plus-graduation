package ewm.comment.mapper;

import ewm.comment.dto.CommentDto;
import ewm.comment.dto.InputCommentDto;
import ewm.comment.model.Comment;
import ewm.event.model.Event;
import ewm.feign.user.UserShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "created", ignore = true)
    Comment toComment(InputCommentDto inputCommentDto, UserShortDto author, Event event);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "author", source = "author")
    CommentDto toCommentDto(Comment comment, UserShortDto author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorId", source = "commentDto.author.id")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    Comment toComment(CommentDto commentDto, Event event);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "author", ignore = true)
    List<CommentDto> toCommentDtos(List<Comment> comments);
}