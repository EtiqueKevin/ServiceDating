package crazy.charlyday.optimisation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Benchmarking current solver...")
public class OptiBenchmark {
    private static final String inputDirectory1 = "ressources/problemes_test/01_pb_simples/";
    private static final String inputDirectory2 = "ressources/problemes_test/02_pb_complexes/";

    @Order(1)
    @DisplayName("Files:")
    @ParameterizedTest(name = "{0}")
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

    void runTests(String inputFile) throws IOException {
        var fullPath = inputDirectory1 + inputFile;
        int score = getScore(fullPath);
        assertThat(score)
                .as("%s: %d", inputFile, score)
                .isGreaterThan(0)
                .withFailMessage("%s: %d", inputFile, score);
    }

    int getScore(String inputFile) {
        return 0;
    }
}
