package crazy.charlyday.optimisation.entities;

import java.io.Serializable;
import java.util.Map;

public record Salarie(String name, Map<SkillType, Integer> competences) implements Serializable {
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
