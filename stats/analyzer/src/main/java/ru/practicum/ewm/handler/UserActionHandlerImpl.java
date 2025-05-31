package ru.practicum.ewm.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.repository.UserActionRepository;
import ru.practicum.ewm.stats.avro.UserActionAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActionHandlerImpl implements UserActionHandler {

    private final UserActionRepository userActionRepository;

    @Override
    public void handleUserAction(UserActionAvro avro) {
        Long eventId = action.getEventId();
        Long userId = action.getUserId();
        Float newActionMark = switch (action.getActionType()) {
            case LIKE -> like;
            case REGISTER -> register;
            case VIEW -> view;
        };

        if (!userActionRepository.existsByEventIdAndUserId(eventId, userId)) {
            userActionRepository.save(UserActionMapper.mapToUserAction(action));
        } else {
            UserAction userAction = userActionRepository.findByEventIdAndUserId(eventId, userId);
            if (userAction.getMark() < newActionMark) {
                userAction.setMark(newActionMark);
                userAction.setTimestamp(action.getTimestamp());
            }
        }
    }
}
}
