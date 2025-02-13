package crazy.charlyday.optimisation.services.algos;

import crazy.charlyday.optimisation.entities.Besoin;
import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.entities.Salarie;
import crazy.charlyday.optimisation.interfaces.Solver;

import java.util.HashMap;
import java.util.Map;

public class DummieSolver implements Solver {
    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        Map<Salarie, Besoin> assignations = new HashMap<>();

        for(Salarie salarie : datingProblem.salaries()) {
            assignations.put(salarie, datingProblem.clients().getFirst().besoins().getFirst());
        }

        return new DatingSolution(0, assignations);
    }
}
