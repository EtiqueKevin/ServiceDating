package crazy.charlyday.optimisation.interfaces;

import crazy.charlyday.optimisation.services.ScoreCalculator;
import crazy.charlyday.optimisation.services.algos.DummieSolver;
import crazy.charlyday.optimisation.services.algos.GloutonSolver;

public class SolverFactory {
    private static Solver solver = new GloutonSolver();

    public static Solver getSolver() {
        return new ScoreCalculator(solver);
    }
}
