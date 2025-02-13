package crazy.charlyday.optimisation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ClientDto {
    @Schema(name = "name", example = "Hugues")
    private String name;

    @Schema(name = "besoins", example = "[{client: Hugues, skill: JD}, ...]")
    private List<BesoinDto> besoins;
}
