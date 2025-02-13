package crazy.charlyday.optimisation.interfaces;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;

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
        // TODO : really compute the score
        return new DatingSolution(3, solution.assignations());
    }
}
