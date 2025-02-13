package crazy.charlyday.optimisation.mappers;

import crazy.charlyday.optimisation.dtos.DatingSolutionDto;
import crazy.charlyday.optimisation.entities.DatingSolution;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DatingSolutionMapper {
    DatingSolutionMapper INSTANCE = Mappers.getMapper(DatingSolutionMapper.class);

    DatingSolutionDto mapToDTO(DatingSolution entity);

    DatingSolution mapToEntity(DatingSolution entity);
}
