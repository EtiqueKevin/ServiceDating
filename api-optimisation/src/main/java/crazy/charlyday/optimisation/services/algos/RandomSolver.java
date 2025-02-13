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

    double timeout = 100;
    double startTime = 0;

    public RandomSolver() {}
    public RandomSolver(double timeout) {
        this.timeout = timeout;
    }

    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        Random random = new Random();
        startTime = System.currentTimeMillis();

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

        while (System.currentTimeMillis() - startTime < timeout) {
            for(int j = 0; j < 10; j++) {
                int index = random.nextInt(input.length);
                input[index] = random.nextInt(maxValue);
            }
            DatingSolution otherSolution = getSolution(datingProblem, besoins, input);
            if(solution.score() < otherSolution.score()) {
                solution = otherSolution;
            }
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
