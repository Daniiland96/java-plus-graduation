package ewm.compilation.mapper;

import ewm.compilation.dto.CompilationDto;
import ewm.compilation.model.Compilation;
import ewm.dto.event.EventShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    Compilation toCompilation(CompilationDto compilationDto);

    @Mapping(target = "events", source = "eventDtos")
    CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> eventDtos);

    @Mapping(target = "events", ignore = true)
    List<CompilationDto> toCompilationDtos(List<Compilation> compilations);
}
