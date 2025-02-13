package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.entities.Salarie;
import crazy.charlyday.optimisation.entities.SkillType;

import java.util.HashMap;
import java.util.Map;

public class AssignationsScore implements ScoreFunction {

    @Override
    public int getScore(DatingProblem problem, DatingSolution solution, int currentScore) {
        Map<String, Integer> clientSatisfaction = new HashMap<>();
        var assignations = solution.assignations();

        for(Salarie salarie : assignations.keySet()) {
            if(!salarie.competences().keySet().containsAll(assignations.get(salarie).skills())) {
                currentScore -= 100000;
                continue;
            }

            var besoin = assignations.get(salarie);
            var satisfaction = clientSatisfaction.getOrDefault(besoin.client(), 0);

            var skillScore = 0;
            for(SkillType skill : besoin.skills()) {
                skillScore += salarie.competences().get(skill);
            }
            if(skillScore >= 1) {
                currentScore += Math.max(1, skillScore - satisfaction);
            }

            clientSatisfaction.put(besoin.client(), ++satisfaction);
        }

        return currentScore;
    }
}
