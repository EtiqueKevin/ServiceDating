package crazy.charlyday.optimisation.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record DatingSolution(int score, LinkedHashMap<Salarie, Besoin> assignations) {
    public static DatingSolution fromCsv(String filePath) throws IOException {
        LinkedHashMap<Salarie, Besoin> matches = new LinkedHashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Récupérer la première ligne pour le score
        int score = Integer.parseInt(lines.get(0));

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(";");
            String clientName = parts[0];
            String skill = parts[1].split(";")[0];
            String salarieName = parts[2];

            SkillType skillType = SkillType.valueOf(skill);
            Salarie salarie = new Salarie(salarieName, Map.of(skillType, 1));
            Besoin besoin = new Besoin(clientName, List.of(skillType));

            matches.put(salarie, besoin);
        }
        return new DatingSolution(score, matches);
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(score).append(";\n");

        for (Map.Entry<Salarie, Besoin> entry : assignations.entrySet()) {
            Salarie salarie = entry.getKey();
            Besoin besoin = entry.getValue();
            sb.append(salarie.name()).append(";")
                .append(besoin.skillCSV())
                .append(besoin.client()).append(";\n");
        }

        return sb.toString();
    }
}
