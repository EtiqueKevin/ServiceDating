package crazy.charlyday.optimisation.services.algos.ecj;

import crazy.charlyday.optimisation.entities.*;
import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.services.ScoreCalculator;
import ec.Evolve;
import ec.vector.IntegerVectorIndividual;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

public class EcjSolver implements Solver {

    public EcjSolver() {

    }

    public EcjSolver(long timeout) {
        EcjTimeoutEvolutionState.timeoutMillis = timeout;
    }

    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        List<Besoin> besoins = new ArrayList<>();
        for(Client client : datingProblem.clients()) {
            besoins.addAll(client.besoins());
        }

        String serializedParams;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(datingProblem);
            oos.close();

            serializedParams = Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        startECJ(datingProblem, besoins, serializedParams);

        try {
            String path = "src/main/resources/ecj_output/out.stat";
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            String prevLine = line;
            while (line != null) {
                prevLine = line;
                line = br.readLine();
            }
            String[] values = prevLine.split(" ");
            int[] intValues = new int[values.length];
            for (int i = 0; i < intValues.length; i++) {
                intValues[i] = Integer.parseInt(values[i]);
            }

            return getSolution(intValues, datingProblem, besoins);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startECJ(DatingProblem datingProblem, List<Besoin> besoins, String serializedParams) {
        Thread thread = new Thread(() -> {
            FakeEvolve.main(List.of(
                            "-file", "src/main/resources/params/params.params",
                            "-p", "pop.subpop.0.species.genome-size=" + besoins.size(),
                            "-p", "pop.subpop.0.species.min-gene=" + 0,
                            "-p", "pop.subpop.0.species.max-gene=" + (datingProblem.salaries().size() - 1),
                            "-p", "params=" + serializedParams)
                    .toArray(new String[0])
            );
        });
        thread.start();
        try{
            thread.join();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private DatingSolution getSolution(int[] genome, DatingProblem datingProblem, List<Besoin> besoins) {
        LinkedHashMap<Salarie, Besoin> assignations = new LinkedHashMap<>();

        for(int i = 0; i < besoins.size(); i++) {
            if(genome.length == i) {
                System.out.println("---");
                System.out.println(genome.length);
                System.out.println(besoins.size());
            }
            Besoin besoin = besoins.get(i);
            Salarie salarie = datingProblem.salaries().get(genome[i]);
            assignations.put(salarie, besoin);
        }

        return ScoreCalculator.computeScore(datingProblem, new DatingSolution(0, assignations));
    }
}
