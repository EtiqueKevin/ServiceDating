package crazy.charlyday.optimisation.dtos;

import crazy.charlyday.optimisation.entities.SkillType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class BesoinDto {
    @Schema(name = "client", example = "Hugues")
    private String client;

    @Schema(name = "skills", example = "JD")
    private List<SkillType> skills;
}
