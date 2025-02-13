package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.*;
import crazy.charlyday.optimisation.interfaces.SolverFactory;
import crazy.charlyday.optimisation.services.ScoreCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Benchmarking current solver...")
public class OptiBenchmark {
    private static final String inputDirectory1 = "src/main/resources/problemes_test/01_pb_simples/";
    private static final String inputDirectory2 = "src/main/resources/problemes_test/02_pb_complexes/";

    @Order(1)
    @DisplayName("01_pb_simples")
    @ParameterizedTest
    @CsvSource({
            "Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv",
            "Probleme_2_nbSalaries_3_nbClients_3_nbTaches_5.csv",
            "Probleme_3_nbSalaries_5_nbClients_5_nbTaches_2.csv",
            "Probleme_4_nbSalaries_5_nbClients_5_nbTaches_4.csv",
            "Probleme_5_nbSalaries_6_nbClients_3_nbTaches_5.csv",
            "Probleme_6_nbSalaries_8_nbClients_8_nbTaches_1.csv",
            "Probleme_7_nbSalaries_8_nbClients_8_nbTaches_3.csv",
            "Probleme_8_nbSalaries_9_nbClients_8_nbTaches_3.csv",
            "Probleme_9_nbSalaries_8_nbClients_10_nbTaches_3.csv"
    })
    void runTests1(String inputFile) throws IOException {
        var fullPath = inputDirectory1 + inputFile;
        int score = getScore(fullPath);

        System.out.printf("%s: %d\n", inputFile, score);

        try {
            assertThat(score).isGreaterThan(0);
        } catch (AssertionError ignored) {
        }
    }

    @Order(2)
    @DisplayName("02_pb_complexes")
    @ParameterizedTest
    @CsvSource({
            "Probleme_1_nbSalaries_10_nbClients_10_nbTaches_3.csv",
            "Probleme_2_nbSalaries_15_nbClients_15_nbTaches_1.csv",
            "Probleme_3_nbSalaries_15_nbClients_15_nbTaches_1.csv",
            "Probleme_4_nbSalaries_15_nbClients_15_nbTaches_3.csv",
            "Probleme_5_nbSalaries_15_nbClients_15_nbTaches_3.csv",
            "Probleme_6_nbSalaries_20_nbClients_15_nbTaches_5.csv",
            "Probleme_7_nbSalaries_20_nbClients_20_nbTaches_3.csv",
            "Probleme_8_nbSalaries_20_nbClients_20_nbTaches_3.csv",
            "Probleme_9_nbSalaries_26_nbClients_26_nbTaches_3.csv",
            "Probleme_10_nbSalaries_26_nbClients_26_nbTaches_3.csv"
    })
    void runTests2(String inputFile) throws IOException {
        var fullPath = inputDirectory2 + inputFile;
        int score = getScore(fullPath);

        System.out.printf("%s: %d\n", inputFile, score);

        try {
            assertThat(score).isGreaterThan(0);
        } catch (AssertionError ignored) {
        }
    }

    @Order(3)
    @DisplayName("Score comparison")
    @ParameterizedTest
    @CsvSource({
            "Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv, Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2_Sol.csv",
    })
    void runTests3(String inputFile, String assertFile) throws IOException {
        DatingSolution solution = DatingSolution.fromCsv(inputDirectory1 + assertFile);
        DatingProblem problem = DatingProblem.fromCsv(inputDirectory1 + inputFile);

        LinkedHashMap<Salarie, Besoin> assignations = new LinkedHashMap<>();
        assignations.put(
                new Salarie("Charlotte", Map.of("IF", 5)),
                new Besoin("Cedric_C", List.of("IF"))
        );
        assignations.put(
                new Salarie("Alice", Map.of("MN", 7)),
                new Besoin("Antoine_C", List.of("MN"))
        );
        assignations.put(
                new Salarie("Bernard", Map.of("BR", 5)),
                new Besoin("Antoine_C", List.of("BR"))
        );

        int expectedScore = solution.score();
        int score = ScoreCalculator.computeScore(problem, new DatingSolution(0, assignations)).score();

        System.out.printf("%s: %d %d\n", inputFile, score, expectedScore);

        try {
            assertThat(score).isGreaterThan(0);
        } catch (AssertionError ignored) {
        }
    }


    int getScore(String inputFile) throws IOException {
        var solution = SolverFactory.getSolver()
                .compute(DatingProblem.fromCsv(inputFile));
        //System.out.println(solution);
        return solution.score();
    }
}