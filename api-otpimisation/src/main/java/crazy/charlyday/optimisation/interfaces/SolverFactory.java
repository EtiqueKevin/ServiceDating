package crazy.charlyday.optimisation.interfaces;

import crazy.charlyday.optimisation.services.ScoreCalculator;
import crazy.charlyday.optimisation.services.algos.DummieSolver;

public class SolverFactory {
    public static Solver getSolver() {
        return new ScoreCalculator(new DummieSolver());
    }
}
