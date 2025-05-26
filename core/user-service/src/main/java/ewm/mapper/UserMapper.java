package ewm.mapper;

import ewm.dto.user.UserDto;
import ewm.dto.user.UserShortDto;
import ewm.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    UserShortDto toUserShortDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

    User toUser(UserDto userDto);

    List<UserShortDto> toUserShortDto(List<User> users);
}