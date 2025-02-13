package crazy.charlyday.optimisation.services.algos;

import crazy.charlyday.optimisation.entities.*;
import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.services.ScoreCalculator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GloutonSolver implements Solver {
    List<Salarie> salaries;
    List<Besoin> besoins = new ArrayList<>();
    LinkedHashMap<Salarie, Besoin> assignations = new LinkedHashMap<>();
    LinkedHashMap<Salarie, Besoin> bestAssignations = new LinkedHashMap<>();
    int bestAssignationsScore = 0;
    DatingProblem problem;

    double timeout = 100;
    double startTime = 0;

    public GloutonSolver() {}

    public GloutonSolver(double timeout) {
        this.timeout = timeout;
    }

    @Override
    public DatingSolution compute(DatingProblem datingProblem) {
        bestAssignations.clear();
        assignations.clear();
        besoins.clear();
        bestAssignationsScore = 0;
        startTime = System.currentTimeMillis();

        problem = datingProblem;
        salaries = datingProblem.salaries();

        {
            int i = 0;
            boolean running = true;
            var clients = datingProblem.clients();
            while (running) {
                running = false;
                for (Client client : clients) {
                    var clientsBesoins = client.besoins();
                    if(clientsBesoins.size() > i) {
                        besoins.add(clientsBesoins.get(i));
                        running = true;
                    }
                }
                i++;
            }
        }

        assignSalaries(0);

        return new DatingSolution(0, bestAssignations);
    }

    void assignSalaries(int index) {
        if(System.currentTimeMillis() - startTime > timeout) return;

        if (index == besoins.size()) {
            int score = ScoreCalculator.computeScore(problem, new DatingSolution(0, new LinkedHashMap<>(assignations))).score();

            if (score > bestAssignationsScore) {
                bestAssignationsScore = score;
                bestAssignations = new LinkedHashMap<>(assignations);
            }
            return;
        }

        Besoin currentBesoin = besoins.get(index);
        List<Salarie> sortedSalaries = getSortedSalaries(currentBesoin);

        for (Salarie salarie : sortedSalaries) {
            if (!assignations.containsKey(salarie)) {
                assignations.put(salarie, currentBesoin);

                assignSalaries(index + 1);
                assignations.remove(salarie);
            }
        }

        if(index < besoins.size()) {
            assignSalaries(index + 1);
        }
    }

    private List<Salarie> getSortedSalaries(Besoin currentBesoin) {
        List<Salarie> matchingSalaries = new ArrayList<>();
        for (Salarie salarie : salaries) {
            if(salarie.competences().keySet().containsAll(currentBesoin.skills())) {
                matchingSalaries.add(salarie);
            }
        }

        matchingSalaries.sort((s1, s2) -> {
            int skillCountComparison = Integer.compare(s1.competences().size(), s2.competences().size());

            if (skillCountComparison == 0) {
                return computeCompetence(s2, currentBesoin) - computeCompetence(s1, currentBesoin);
            }

            return skillCountComparison;
        });

        return matchingSalaries;
    }

    int computeCompetence(Salarie salarie, Besoin besoin) {
        int competence = 0;
        for (String skill : besoin.skills()) {
            competence += salarie.competences().getOrDefault(skill, 0);
        }
        return competence;
    }
}
