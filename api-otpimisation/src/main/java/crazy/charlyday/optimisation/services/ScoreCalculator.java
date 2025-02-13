package crazy.charlyday.optimisation.services;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.services.score.ScoreFunction;
import crazy.charlyday.optimisation.services.score.UniqueSalarieConstraint;

import java.util.List;

public class ScoreCalculator implements Solver {

    private Solver solver;
    public ScoreCalculator(Solver solver) {
        this.solver = solver;
    }

    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        return computeScore(solver.compute(datingProblem));
    }

    private DatingSolution computeScore(DatingSolution solution) {
        List<ScoreFunction> scoreFunctions = List.of(
                new UniqueSalarieConstraint()
        );

        int score = 0;
        for(ScoreFunction scoreFunction : scoreFunctions) {
            score = scoreFunction.getScore(solution, score);
        }

        return new DatingSolution(score, solution.assignations());
    }
}
