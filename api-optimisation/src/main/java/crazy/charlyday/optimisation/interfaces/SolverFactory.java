package crazy.charlyday.optimisation.interfaces;

import crazy.charlyday.optimisation.services.ScoreCalculator;
import crazy.charlyday.optimisation.services.algos.GloutonSolver;
import crazy.charlyday.optimisation.services.algos.ecj.EcjSolver;
import crazy.charlyday.optimisation.services.algos.RandomSolver;

public class SolverFactory {
    private static Solver solver = new EcjSolver();

    public static Solver getSolver() {
        return new ScoreCalculator(solver);
    }
}
