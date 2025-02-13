package crazy.charlyday.optimisation.mappers;

import crazy.charlyday.optimisation.dtos.SalarieDto;
import crazy.charlyday.optimisation.entities.Salarie;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SalarieMapper {
    SalarieMapper INSTANCE = Mappers.getMapper(SalarieMapper.class);

    SalarieDto mapToDTO(Salarie entity);

    @InheritInverseConfiguration
    Salarie mapToEntity(SalarieDto dto);
}
