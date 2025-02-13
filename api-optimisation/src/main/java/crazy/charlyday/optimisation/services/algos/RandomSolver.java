package crazy.charlyday.optimisation.services.algos;

import crazy.charlyday.optimisation.entities.*;
import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.services.ScoreCalculator;
import ec.vector.IntegerVectorIndividual;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class RandomSolver implements Solver {
    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        Random random = new Random();

        List<Besoin> besoins = new ArrayList<>();
        for(Client client : datingProblem.clients()) {
            besoins.addAll(client.besoins());
        }

        int maxValue = datingProblem.salaries().size();

        int[] input = new int[besoins.size()];
        for(int i = 0; i < besoins.size(); i++) {
            input[i] = random.nextInt(maxValue);
        }

        DatingSolution solution = getSolution(datingProblem, besoins, input);
        for(int i = 0; i < 500000; i++) {
            for(int j = 0; j < 10; j++) {
                int index = random.nextInt(input.length);
                input[index] = random.nextInt(maxValue);
            }
            DatingSolution otherSolution = getSolution(datingProblem, besoins, input);
            if(solution.score() < otherSolution.score()) {
                solution = otherSolution;
            }
        }
        for(int i = 0; i < besoins.size(); i++) {
            input[i] = random.nextInt(maxValue);
        }

        return solution;
    }

    private DatingSolution getSolution(DatingProblem problem, List<Besoin> besoins, int[] input) {
        LinkedHashMap<Salarie, Besoin> assignations = new LinkedHashMap<>();

        for(int i = 0; i < besoins.size(); i++) {
            Besoin besoin = besoins.get(i);
            Salarie salarie = problem.salaries().get(input[i]);
            assignations.put(salarie, besoin);
        }

        return ScoreCalculator.computeScore(problem, new DatingSolution(0, assignations));
    }
}
