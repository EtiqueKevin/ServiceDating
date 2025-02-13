package crazy.charlyday.optimisation.mappers;

import crazy.charlyday.optimisation.dtos.AssignationDto;
import crazy.charlyday.optimisation.dtos.BesoinDto;
import crazy.charlyday.optimisation.dtos.DatingSolutionDto;
import crazy.charlyday.optimisation.entities.Besoin;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.entities.Salarie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.LinkedHashMap;
import java.util.Map;

@Mapper
public interface DatingSolutionMapper {
    DatingSolutionMapper INSTANCE = Mappers.getMapper(DatingSolutionMapper.class);

    @Mapping(target = "assignations", expression = "java(mapAssignations(entity.assignations()))")
    DatingSolutionDto mapToDTO(DatingSolution entity);

    default LinkedHashMap<String, AssignationDto> mapAssignations(Map<Salarie, Besoin> assignations) {
        LinkedHashMap<String, AssignationDto> result = new LinkedHashMap<>();
        for (Map.Entry<Salarie, Besoin> entry : assignations.entrySet()) {
            result.put(entry.getKey().name(), new AssignationDto(
                    new BesoinDto(entry.getValue().client(), entry.getValue().skills())
            ));
        }
        return result;
    }
}
