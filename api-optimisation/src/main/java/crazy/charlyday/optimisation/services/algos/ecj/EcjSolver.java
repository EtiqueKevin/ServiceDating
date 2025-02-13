package crazy.charlyday.optimisation.services.algos.ecj;

import crazy.charlyday.optimisation.entities.Besoin;
import crazy.charlyday.optimisation.entities.Client;
import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.entities.DatingSolution;
import crazy.charlyday.optimisation.interfaces.Solver;
import ec.Evolve;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class EcjSolver implements Solver {
    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        List<Besoin> besoins = new ArrayList<>();
        for(Client client : datingProblem.clients()) {
            besoins.addAll(client.besoins());
        }

        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(datingProblem);
            oos.close();

            String serializedParams = Base64.getEncoder().encodeToString(baos.toByteArray());
            Evolve.main(List.of(
                            "-file","ressources/params/params.params",
                            "-p","pop.subpop.0.species.genome-size=" + besoins.size(),
                            "-p", "pop.subpop.0.species.min-gene=" + 0,
                            "-p", "pop.subpop.0.species.max-gene=" + (besoins.size() - 1),
                            "-p","params=" + serializedParams)
                    .toArray(new String[0])
            );
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        return null;
    }
}
