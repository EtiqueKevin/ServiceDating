package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.DatingProblem;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // test datingProblemMapper
        DatingProblem datingProblem = DatingProblem.fromCsv("src/main/resources/problemes_test/00_exemple/metier_1.csv");
        System.out.println(datingProblem);
    }
}