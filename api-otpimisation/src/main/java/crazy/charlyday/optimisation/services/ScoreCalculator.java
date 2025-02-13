package crazy.charlyday.optimisation.services;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.interfaces.Solver;

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
