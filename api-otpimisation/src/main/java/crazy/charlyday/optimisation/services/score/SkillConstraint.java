package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.entities.Salarie;

public class SkillConstraint implements ScoreFunction {

    @Override
    public int getScore(DatingSolution solution, int currentScore) {
        var assignations = solution.assignations();
        for(Salarie salarie : assignations.keySet()) {
            if(!salarie.competences().containsKey(assignations.get(salarie).skill())) {
                return -1;
            }
        }

        return currentScore;
    }
}
