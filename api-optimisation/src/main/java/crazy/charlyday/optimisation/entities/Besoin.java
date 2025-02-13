package crazy.charlyday.optimisation.entities;

import java.util.List;

public record Besoin(String client, List<String> skills) {
    public String skillCSV() {
        StringBuilder s = new StringBuilder();
        for (String skill : skills) {
            s.append(skill).append(";");
        }
        return s.toString();
    }
}
