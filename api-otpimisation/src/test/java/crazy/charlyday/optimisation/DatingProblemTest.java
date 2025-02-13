package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.DatingProblem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

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


}
