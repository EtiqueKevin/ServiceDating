package crazy.charlyday.optimisation.DTOs;

import crazy.charlyday.optimisation.entities.Besoin;
import crazy.charlyday.optimisation.entities.Salarie;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class DatingSolutionDTO {
    @Schema(name = "score", example = "1")
    private int score;

    @Schema(name = "assignations", example = "{Salarie1: Besoin1}")
    private Map<Salarie, Besoin> assignations;
}
