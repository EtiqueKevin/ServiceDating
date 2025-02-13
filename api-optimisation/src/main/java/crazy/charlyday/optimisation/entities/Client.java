package crazy.charlyday.optimisation.entities;

import java.util.List;

public record Client(String name, List<Besoin> besoins) {
}
