package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DatingSolutionTest {
    @Order(1)
    @DisplayName("DATING_SOLUTION_TEST_TO_CSV")
    @Test
    void toCsvTest() {
        Salarie salarie1 = new Salarie("Charlotte", Map.of("IF", 1));
        Salarie salarie2 = new Salarie("Alice", Map.of("MN", 1));
        Salarie salarie3 = new Salarie("Bernard", Map.of("BR", 1));

        Client client1 = new Client("Cedric_C", List.of(new Besoin("Cedric_C", List.of("IF"))));
        Client client2 = new Client("Antoine_C", List.of(new Besoin("Antoine_C", List.of("MN"))));
        Client client3 = new Client("Antoine_C", List.of(new Besoin("Antoine_C", List.of("BR"))));

        LinkedHashMap<Salarie, Besoin> map = new LinkedHashMap<>();
        map.put(salarie1, client1.besoins().getFirst());
        map.put(salarie2, client2.besoins().getFirst());
        map.put(salarie3, client3.besoins().getFirst());

        DatingSolution solution = new DatingSolution(6, map);

        String csv = solution.toCsv();

        String expectedCsv = """
                6;
                Charlotte;IF;Cedric_C;
                Alice;MN;Antoine_C;
                Bernard;BR;Antoine_C;
                """;

        assertEquals(expectedCsv, csv);
    }

    @Order(2)
    @DisplayName("DATING_SOLUTION_TEST_FROM_CSV")
    @Test
    void fromCsvTest() {
        // Fichier CSV d'entrée
        String inputFilePath = "src/main/resources/problemes_test/01_pb_simples/Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2_Sol.csv"; // Chemin vers le fichier CSV pour ce test

        // Création de l'objet DatingSolution attendu
        Salarie salarie1 = new Salarie("Charlotte", Map.of("IF", 1));
        Salarie salarie2 = new Salarie("Alice", Map.of("MN", 1));
        Salarie salarie3 = new Salarie("Bernard", Map.of("BR", 1));

        Client client1 = new Client("Cedric_C", List.of(new Besoin("Cedric_C", List.of("IF"))));
        Client client2 = new Client("Antoine_C", List.of(new Besoin("Antoine_C", List.of("MN"))));
        Client client3 = new Client("Antoine_C", List.of(new Besoin("Antoine_C", List.of("BR"))));

        LinkedHashMap<Salarie, Besoin> map = new LinkedHashMap<>();
        map.put(salarie1, client1.besoins().getFirst());
        map.put(salarie2, client2.besoins().getFirst());
        map.put(salarie3, client3.besoins().getFirst());

        DatingSolution expectedSolution = new DatingSolution(6, map);

        try {
            // Appel de la méthode fromCsv et récupération de l'objet DatingSolution
            DatingSolution actualSolution = DatingSolution.fromCsv(inputFilePath);

            // Vérification de l'égalité entre l'objet attendu et l'objet chargé depuis le fichier CSV
            assertEquals(expectedSolution, actualSolution, "L'objet DatingSolution reconstruit doit correspondre à l'attendu.");
        } catch (Exception e) {
            fail();
        }
    }
}
