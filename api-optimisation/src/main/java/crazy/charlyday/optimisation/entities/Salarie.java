package crazy.charlyday.optimisation.entities;

import java.util.Map;

public record Salarie(String name, Map<String, Integer> competences) {
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Salarie salarie) {
            if(salarie.name().equals(name)) return true;
        }
        return false;
    }
}
