package crazy.charlyday.optimisation.mappers;

import crazy.charlyday.optimisation.dtos.BesoinDto;
import crazy.charlyday.optimisation.entities.Besoin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BesoinMapper {
    BesoinMapper INSTANCE = Mappers.getMapper(BesoinMapper.class);

    BesoinDto mapToDTO(Besoin entity);

    Besoin mapToEntity(BesoinDto dto);
}
