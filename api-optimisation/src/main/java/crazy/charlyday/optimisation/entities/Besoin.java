package crazy.charlyday.optimisation.entities;

import java.io.Serializable;
import java.util.List;

public record Besoin(String client, List<SkillType> skills) implements Serializable {
    public String skillCSV() {
        StringBuilder s = new StringBuilder();
        for (SkillType skill : skills) {
            s.append(skill.name()).append(";");
        }
        return s.toString();
    }
}
