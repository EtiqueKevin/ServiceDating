package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;

public interface ScoreFunction {
    int getScore(DatingProblem problem, DatingSolution solution, int currentScore);
}
