package ewm.service;

import ewm.dto.UserDto;
import ewm.dto.UserShortDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    List<UserDto> getAll(List<Long> ids, Integer from, Integer size);

    UserDto create(UserDto userDto);

    void delete(Long id);

    UserShortDto findUserShortDtoById(Long userId);

    Map<Long, UserShortDto> findUserShortDtoById(Set<Long> usersId);

}
