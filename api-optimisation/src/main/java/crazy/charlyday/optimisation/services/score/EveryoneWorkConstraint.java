package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.entities.Salarie;

public class EveryoneWorkConstraint implements ScoreFunction {
    @Override
    public int getScore(DatingProblem problem, DatingSolution solution, int currentScore) {
        for(Salarie salarie : problem.salaries()) {
            if(!solution.assignations().containsKey(salarie)) {
                currentScore -= 10;
            }
        }

        return currentScore;
    }
}
