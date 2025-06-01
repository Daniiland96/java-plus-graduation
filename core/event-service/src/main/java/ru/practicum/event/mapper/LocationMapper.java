package ru.practicum.event.mapper;

import ru.practicum.dto.event.LocationDto;
import ru.practicum.event.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);

}
