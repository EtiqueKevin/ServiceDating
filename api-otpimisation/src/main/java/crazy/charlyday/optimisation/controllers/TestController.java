package crazy.charlyday.optimisation.controllers;

import crazy.charlyday.optimisation.dtos.DatingProblemDto;
import crazy.charlyday.optimisation.entities.DatingProblem;
import crazy.charlyday.optimisation.mappers.DatingProblemMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public DatingProblemDto test() {
        try {
            return DatingProblemMapper.INSTANCE.mapToDTO(DatingProblem.fromCsv("src/main/resources/problemes_test/01_pb_simples/Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
