package crazy.charlyday.optimisation.services.score;

import crazy.charlyday.optimisation.entities.Besoin;
import crazy.charlyday.optimisation.entities.Client;
import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;

public class EveryoneServedConstraint implements ScoreFunction {

    @Override
    public int getScore(DatingProblem problem, DatingSolution solution, int currentScore) {
        for(Client client : problem.clients()) {
            boolean found = false;

           for(Besoin besoin : solution.assignations().values()) {
               if(client.besoins().contains(besoin)) {
                   found = true;
                   break;
               }
           }

           if(!found) {
               currentScore -= 10;
           }
        }

        return currentScore;
    }
}
