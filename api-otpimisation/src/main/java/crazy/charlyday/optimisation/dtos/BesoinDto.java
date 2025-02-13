package crazy.charlyday.optimisation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class BesoinDto {
    @Schema(name = "client", example = "Hugues")
    private String client;

    @Schema(name = "skill", example = "JD")
    private String skill;
}
