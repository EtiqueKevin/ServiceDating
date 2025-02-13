package crazy.charlyday.optimisation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DatingProblemDto {
    @Schema(name = "client", example = "{name: Hugues, besoins: [{client: Hugues, skill: JD}, ...]}")
    private ClientDto client;

    @Schema(name = "salarie", example = "{name: Albert, competences: {JD: 1, ...}}")
    private SalarieDto salarie;
}
