package crazy.charlyday.optimisation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class DatingProblemDto {
    @Schema(name = "clients", example = "{name: Hugues, besoins: [{client: Hugues, skill: JD}, ...]}")
    private List<ClientDto> clients;

    @Schema(name = "salaries", example = "{name: Albert, competences: {JD: 1, ...}}")
    private List<SalarieDto> salaries;
}
