package crazy.charlyday.optimisation.services.algos.ecj;

import crazy.charlyday.optimisation.entities.*;
import crazy.charlyday.optimisation.services.ScoreCalculator;
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

public class EcjDatingProblem extends Problem implements SimpleProblemForm {

    private DatingProblem datingProblem;
    List<Besoin> besoins;

    @Override
    public void evaluate(EvolutionState evolutionState, Individual ind, int i, int i1) {
        if(datingProblem == null) {
            loadParams(evolutionState);
        }

        IntegerVectorIndividual individual = (IntegerVectorIndividual)ind;
        DatingSolution solution = getSolution(individual);

        ((SimpleFitness)(individual.fitness)).setFitness(evolutionState, solution.score(),false);
    }

    private DatingSolution getSolution(IntegerVectorIndividual individual) {
        LinkedHashMap<Salarie, Besoin> assignations = new LinkedHashMap<>();

        for(int i = 0; i < besoins.size(); i++) {
            Besoin besoin = besoins.get(i);
            Salarie salarie = datingProblem.salaries().get(individual.genome[i]);
            assignations.put(salarie, besoin);
        }

        return ScoreCalculator.computeScore(datingProblem, new DatingSolution(0, assignations));
    }

    private void loadParams(EvolutionState state) {
        String serializedClass = state.parameters.getString(new Parameter("params"), null);
        try {
            byte[] data = Base64.getDecoder().decode(serializedClass);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            datingProblem = (DatingProblem) ois.readObject();
            ois.close();

            for(Client client : datingProblem.clients()) {
                besoins.addAll(client.besoins());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la deserialization de datingProblem", e);
        }
    }
}
