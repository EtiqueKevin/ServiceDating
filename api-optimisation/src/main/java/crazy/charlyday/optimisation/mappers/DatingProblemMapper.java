package crazy.charlyday.optimisation.mappers;

import crazy.charlyday.optimisation.dtos.DatingProblemDto;
import crazy.charlyday.optimisation.entities.DatingProblem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DatingProblemMapper {
    DatingProblemMapper INSTANCE = Mappers.getMapper(DatingProblemMapper.class);

    DatingProblemDto mapToDTO(DatingProblem entity);

    @InheritInverseConfiguration
    DatingProblem mapToEntity(DatingProblemDto dto);
}
