package crazy.charlyday.optimisation.interfaces;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;

public interface Solver {
    DatingSolution compute(DatingProblem datingProblem);
}
