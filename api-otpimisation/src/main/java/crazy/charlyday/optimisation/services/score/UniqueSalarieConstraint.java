package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.Besoin;
import crazy.charlyday.optimisation.entities.DatingSolution;

import java.util.HashSet;

public class UniqueSalarieConstraint implements ScoreFunction {
    public int getScore(DatingSolution solution, int currentScore) {
        HashSet<Besoin> seen = new HashSet<>();

        for (Besoin besoin : solution.assignations().values()) {
            if (!seen.add(besoin)) {
                return -1;
            }
        }

        return currentScore;
    }
}
