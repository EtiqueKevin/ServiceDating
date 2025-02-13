package crazy.charlyday.optimisation.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public record DatingSolution(int score, LinkedHashMap<Salarie, Besoin> assignations) {
    public static DatingSolution fromCsv(String filePath) throws IOException {
        LinkedHashMap<Salarie, Besoin> matches = new LinkedHashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        int score = Integer.parseInt(lines.getFirst().split(";")[0]);

        Map<String, Salarie> salariesMap = new HashMap<>();
        Map<String, Client> clientsMap = new HashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(";");
            String salarieName = parts[0];
            String skill = parts[1];
            String clientName = parts[2];

            String skillType = skill;

            if (!salariesMap.containsKey(salarieName)) {
                salariesMap.put(salarieName, new Salarie(salarieName, new HashMap<>()));
            }

            Salarie salarie = salariesMap.get(salarieName);
            salarie.competences().merge(skillType, 1, Integer::sum); // Ajouter la compétence avec une fréquence

            if (!clientsMap.containsKey(clientName)) {
                clientsMap.put(clientName, new Client(clientName, new ArrayList<>()));
            }

            Client client = clientsMap.get(clientName);
            client.besoins().add(new Besoin(clientName, List.of(skillType)));

            matches.put(salarie, client.besoins().get(client.besoins().size() - 1));
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
