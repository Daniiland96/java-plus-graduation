package ewm.mapper;

import ewm.dto.request.ParticipationRequestDto;
import ewm.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static ewm.utility.Constants.FORMAT_DATETIME;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "event", source = "eventId")
    @Mapping(target = "requester", source = "requesterId")
    @Mapping(target = "created", source = "createdOn", dateFormat = FORMAT_DATETIME)
    ParticipationRequestDto toParticipationRequestDto(Request request);

    @Mapping(target = "event", source = "eventId")
    @Mapping(target = "requester", source = "requesterId")
    @Mapping(target = "created", source = "createdOn", dateFormat = FORMAT_DATETIME)
    List<ParticipationRequestDto> toParticipationRequestDto(List<Request> requests);
}