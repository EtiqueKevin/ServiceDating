package crazy.charlyday.optimisation.interfaces;

import crazy.charlyday.optimisation.interfaces.algos.DummieSolver;

public class SolverFactory {
    public static Solver getSolver() {
        return new ScoreCalculator(new DummieSolver());
    }
}
