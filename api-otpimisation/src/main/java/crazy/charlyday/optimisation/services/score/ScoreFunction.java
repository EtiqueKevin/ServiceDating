package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.DatingSolution;

public interface ScoreFunction {
    int getScore(DatingSolution solution, int currentScore);
}
