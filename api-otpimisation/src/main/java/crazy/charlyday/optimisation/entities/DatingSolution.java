package crazy.charlyday.optimisation.entities;

import java.util.Map;

public record DatingSolution(int score, Map<Salarie, Besoin> assignations) {
}
