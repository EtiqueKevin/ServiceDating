package crazy.charlyday.optimisation.mappers;

import crazy.charlyday.optimisation.dtos.ClientDto;
import crazy.charlyday.optimisation.entities.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto mapToDTO(Client entity);

    Client mapToEntity(ClientDto dto);
}
