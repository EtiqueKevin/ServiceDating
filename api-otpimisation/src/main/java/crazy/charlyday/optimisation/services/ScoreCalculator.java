package crazy.charlyday.optimisation.services;

import crazy.charlyday.optimisation.entities.*;
import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.services.score.*;

import java.util.List;

public class ScoreCalculator implements Solver {

    private Solver solver;
    public ScoreCalculator(Solver solver) {
        this.solver = solver;
    }

    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        return computeScore(datingProblem, solver.compute(datingProblem));
    }

    public static DatingSolution computeScore(DatingProblem problem, DatingSolution solution) {
        List<ScoreFunction> scoreFunctions = List.of(
                new AssignationsScore(),
                new EveryoneServedConstraint(),
                new EveryoneWorkConstraint(),
                new UniqueSalarieConstraint()
        );

        int score = 0;
        for(ScoreFunction scoreFunction : scoreFunctions) {
            score = scoreFunction.getScore(problem, solution, score);
        }

        return new DatingSolution(score, solution.assignations());
    }
}
