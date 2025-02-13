package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.mappers.DatingProblemCsvMapper;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // test datingProblemMapper
        DatingProblem datingProblem = DatingProblemCsvMapper.fromCsv("src/main/resources/problemes_test/00_exemple/metier_1.csv");
        System.out.println(datingProblem);
    }
}