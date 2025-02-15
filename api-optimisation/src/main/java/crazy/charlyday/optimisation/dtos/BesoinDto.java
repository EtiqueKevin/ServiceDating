package crazy.charlyday.optimisation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BesoinDto {
    @Schema(name = "client", example = "Hugues")
    private String client;

    @Schema(name = "skills", example = "JD")
    private List<String> skills;
}
