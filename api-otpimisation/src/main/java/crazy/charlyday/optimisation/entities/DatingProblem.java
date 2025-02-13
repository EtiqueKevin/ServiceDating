package crazy.charlyday.optimisation.entities;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DatingProblem(List<Client> clients, List<Salarie> salaries) {
    public static DatingProblem fromCsv(String filePath) throws IOException {
        List<Client> clients = new ArrayList<>();
        List<Salarie> salaries = new ArrayList<>();

        Map<String, List<Besoin>> clientMap = new HashMap<>();
        Map<String, Map<SkillType, Integer>> salarieMap = new HashMap<>();

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
                SkillType skillType = SkillType.valueOf(parts[2]);

                clientMap.computeIfAbsent(clientName, k -> new ArrayList<>()).add(new Besoin(clientName, skillType));
            } else {
                // Salarie: index;name;skillType;level
                String salarieName = parts[1];
                SkillType skillType = SkillType.valueOf(parts[2]);
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

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("besoins;;;\n");
        for (Client client : clients) {
            for (Besoin besoin : client.besoins()) {
                sb.append(client.name()).append(";").append(besoin.skill()).append("\n");
            }
        }
        sb.append("competences;;;\n");
        for (Salarie salarie : salaries) {
            for (Map.Entry<SkillType, Integer> entry : salarie.competences().entrySet()) {
                sb.append(salarie.name()).append(";").append(entry.getKey()).append(";").append(entry.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
}
