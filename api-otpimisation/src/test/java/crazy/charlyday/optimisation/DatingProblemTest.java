package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DatingProblemTest {
    private static final String inputDirectory1 = "src/main/resources/problemes_test/01_pb_simples/";
    private static final String inputDirectory2 = "src/main/resources/problemes_test/02_pb_complexes/";

    @Order(1)
    @DisplayName("DATING_PROBLEMS_TEST_FROM_CSV_EXECUTION")
    @ParameterizedTest
    @CsvSource({
            inputDirectory1 + "Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv",
            inputDirectory1 + "Probleme_2_nbSalaries_3_nbClients_3_nbTaches_5.csv",
            inputDirectory1 + "Probleme_3_nbSalaries_5_nbClients_5_nbTaches_2.csv",
            inputDirectory1 + "Probleme_4_nbSalaries_5_nbClients_5_nbTaches_4.csv",
            inputDirectory1 + "Probleme_5_nbSalaries_6_nbClients_3_nbTaches_5.csv",
            inputDirectory1 + "Probleme_6_nbSalaries_8_nbClients_8_nbTaches_1.csv",
            inputDirectory1 + "Probleme_7_nbSalaries_8_nbClients_8_nbTaches_3.csv",
            inputDirectory1 + "Probleme_8_nbSalaries_9_nbClients_8_nbTaches_3.csv",
            inputDirectory1 + "Probleme_9_nbSalaries_8_nbClients_10_nbTaches_3.csv",
            inputDirectory2 + "Probleme_1_nbSalaries_10_nbClients_10_nbTaches_3.csv",
            inputDirectory2 + "Probleme_2_nbSalaries_15_nbClients_15_nbTaches_1.csv",
            inputDirectory2 + "Probleme_3_nbSalaries_15_nbClients_15_nbTaches_1.csv",
            inputDirectory2 + "Probleme_4_nbSalaries_15_nbClients_15_nbTaches_3.csv",
            inputDirectory2 + "Probleme_5_nbSalaries_15_nbClients_15_nbTaches_3.csv",
            inputDirectory2 + "Probleme_6_nbSalaries_20_nbClients_15_nbTaches_5.csv",
            inputDirectory2 + "Probleme_7_nbSalaries_20_nbClients_20_nbTaches_3.csv",
            inputDirectory2 + "Probleme_8_nbSalaries_20_nbClients_20_nbTaches_3.csv",
            inputDirectory2 + "Probleme_9_nbSalaries_26_nbClients_26_nbTaches_3.csv",
            inputDirectory2 + "Probleme_10_nbSalaries_26_nbClients_26_nbTaches_3.csv"
    })
    void fromCsvTestExecution(String inputFile) {
        try {
            // Convertir le fichier en DatingProblem
            DatingProblem problem = DatingProblem.fromCsv(inputFile);

            // Vérifier que les listes ne sont pas vides
            assertNotNull(problem, "Le DatingProblem ne doit pas être null");
            assertFalse(problem.clients().isEmpty(), "La liste des clients ne doit pas être vide");
            assertFalse(problem.salaries().isEmpty(), "La liste des salariés ne doit pas être vide");

            // Vérifier que chaque client a au moins un besoin
            problem.clients().forEach(client ->
                    assertFalse(client.besoins().isEmpty(), "Chaque client doit avoir au moins un besoin"));

            // Vérifier que chaque salarié a au moins une compétence
            problem.salaries().forEach(salarie ->
                    assertFalse(salarie.competences().isEmpty(), "Chaque salarié doit avoir au moins une compétence"));

        } catch (IOException e) {
            fail("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }
    }

    @Order(2)
    @DisplayName("DATING_PROBLEMS_TEST_FROM_CSV")
    @Test
    void fromCsvTest() {
        // Création des clients avec leurs besoins
        List<Client> clients = new ArrayList<>(List.of(
                new Client("Antoine_C", List.of(
                        new Besoin("Antoine_C", List.of(SkillType.BR)),
                        new Besoin("Antoine_C", List.of(SkillType.MN))
                )),
                new Client("Brigitte_C", List.of(
                        new Besoin("Brigitte_C", List.of(SkillType.MN))
                )),
                new Client("Cedric_C", List.of(
                        new Besoin("Cedric_C", List.of(SkillType.IF))
                ))
        ));

        // Création des salariés avec leurs compétences
        List<Salarie> salaries = new ArrayList<>(List.of(
                new Salarie("Alice", Map.of(SkillType.BR, 4, SkillType.MN, 7, SkillType.AD, 1)),
                new Salarie("Bernard", Map.of(SkillType.BR, 5, SkillType.AD, 7)),
                new Salarie("Charlotte", Map.of(SkillType.JD, 8, SkillType.IF, 5, SkillType.AD, 4))
        ));

        // Tri des listes pour garantir un ordre identique à la lecture depuis CSV
        clients.sort(Comparator.comparing(Client::name));
        salaries.sort(Comparator.comparing(Salarie::name));

        // Création de l'objet DatingProblem attendu
        DatingProblem expectedProblem = new DatingProblem(clients, salaries);

        // Appel de la méthode fromCsv et comparaison avec l'objet attendu
        try {
            DatingProblem actualProblem = DatingProblem.fromCsv("src/main/resources/problemes_test/01_pb_simples/Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv");

            // Tri des listes avant la comparaison pour éviter les erreurs dues à l'ordre
            actualProblem.clients().sort(Comparator.comparing(Client::name));
            actualProblem.salaries().sort(Comparator.comparing(Salarie::name));

            assertEquals(expectedProblem, actualProblem, "L'objet DatingProblem reconstruit doit correspondre à l'attendu.");
        } catch (Exception e) {
            fail("Erreur lors du chargement du fichier CSV : " + e.getMessage());
        }
    }
}
