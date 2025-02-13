package crazy.charlyday.optimisation.services.algos.ecj;

import ec.EvolutionState;
import ec.simple.SimpleEvolutionState;

public class EcjTimeoutEvolutionState extends SimpleEvolutionState  {
    private long startTime;
    public static long timeoutMillis;

    public EcjTimeoutEvolutionState() {    }

    @Override
    public void startFresh() {
        super.startFresh();
        startTime = System.currentTimeMillis();
    }

    @Override
    public int evolve() {
        if (System.currentTimeMillis() - startTime >= timeoutMillis) {
            output.message("Timeout reached! Stopping evolution...");
            return R_FAILURE;
        }
        return super.evolve();
    }
}
