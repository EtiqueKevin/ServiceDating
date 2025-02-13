package crazy.charlyday.optimisation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
public class DatingSolutionDto {
    @Schema(name = "score", example = "1")
    private int score;

    @Schema(name = "assignations", example = "{Salarie1: {client: Hugues, skill: JD}}")
    private LinkedHashMap<String, AssignationDto> assignations;
}
