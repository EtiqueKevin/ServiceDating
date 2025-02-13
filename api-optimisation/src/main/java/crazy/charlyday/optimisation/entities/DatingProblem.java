package crazy.charlyday.optimisation.entities;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DatingProblem(List<Client> clients, List<Salarie> salaries) implements Serializable {
    public static DatingProblem fromCsv(String filePath) throws IOException {
        List<Client> clients = new ArrayList<>();
        List<Salarie> salaries = new ArrayList<>();

        Map<String, List<Besoin>> clientMap = new HashMap<>();
        Map<String, Map<String, Integer>> salarieMap = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(filePath));

        boolean isClientSection = true;

        for (String line : lines) {
            if (line.startsWith("besoins")) {
                isClientSection = true;
                continue;
            } else if (line.startsWith("competences")) {
                isClientSection = false;
                continue;
            }

            String[] parts = line.split(";");
            if (isClientSection) {
                // Client: index;name;skillType
                String clientName = parts[1];
                String skillType = parts[2];

                clientMap.computeIfAbsent(clientName, k -> new ArrayList<>()).add(new Besoin(clientName, List.of(skillType)));
            } else {
                // Salarie: index;name;skillType;level
                String salarieName = parts[1];
                String skillType = parts[2];
                int level = Integer.parseInt(parts[3]);

                salarieMap.computeIfAbsent(salarieName, k -> new HashMap<>()).put(skillType, level);
            }
        }

        // Convert map entries to Client and Salarie objects
        for (var entry : clientMap.entrySet()) {
            clients.add(new Client(entry.getKey(), entry.getValue()));
        }
        for (var entry : salarieMap.entrySet()) {
            salaries.add(new Salarie(entry.getKey(), entry.getValue()));
        }

        return new DatingProblem(clients, salaries);
    }
}
