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
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

public class EcjDatingProblem extends Problem implements SimpleProblemForm {

    @Override
    public void evaluate(EvolutionState evolutionState, Individual ind, int i, int i1) {
        var loading = loadParams(evolutionState);
        var datingProblem = loading.problem;
        var besoins = loading.besoins;

        IntegerVectorIndividual individual = (IntegerVectorIndividual)ind;
        DatingSolution solution = getSolution(individual, datingProblem, besoins);

        ((SimpleFitness)(individual.fitness)).setFitness(evolutionState, solution.score(),false);
    }

    private DatingSolution getSolution(IntegerVectorIndividual individual, DatingProblem datingProblem, List<Besoin> besoins) {
        LinkedHashMap<Salarie, Besoin> assignations = new LinkedHashMap<>();

        for(int i = 0; i < besoins.size(); i++) {
            if(individual.genome.length == i) {
                System.out.println("---");
                System.out.println(individual.genome.length);
                System.out.println(besoins.size());
            }
            Besoin besoin = besoins.get(i);
            Salarie salarie = datingProblem.salaries().get(individual.genome[i]);
            assignations.put(salarie, besoin);
        }

        return ScoreCalculator.computeScore(datingProblem, new DatingSolution(0, assignations));
    }

    public record LoadParamValues(DatingProblem problem, List<Besoin> besoins){}

    private synchronized LoadParamValues loadParams(EvolutionState state) {
        String serializedClass = state.parameters.getString(new Parameter("params"), null);
        try {

            byte[] data = Base64.getDecoder().decode(serializedClass);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            DatingProblem problem = (DatingProblem) ois.readObject();
            ois.close();

            List<Besoin> besoins = new ArrayList<>();
            for(Client client : problem.clients()) {
                besoins.addAll(client.besoins());
            }

            return new LoadParamValues(problem, besoins);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la deserialization de datingProblem", e);
        }
    }
}
