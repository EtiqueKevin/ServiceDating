package crazy.charlyday.optimisation.dtos;

import crazy.charlyday.optimisation.entities.SkillType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SalarieDto {
    @Schema(name = "name", example = "Albert")
    private String name;

    @Schema(name = "competences", example = "{JD: 1, ...}")
    private Map<SkillType, Integer> competences;
}
