package crazy.charlyday.optimisation.controllers;

import crazy.charlyday.optimisation.dtos.DatingProblemDto;
import crazy.charlyday.optimisation.dtos.DatingSolutionDto;
import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.interfaces.Solvers;
import crazy.charlyday.optimisation.mappers.DatingProblemMapper;
import crazy.charlyday.optimisation.mappers.DatingSolutionMapper;
import crazy.charlyday.optimisation.services.ScoreCalculator;
import crazy.charlyday.optimisation.services.algos.GloutonSolver;
import crazy.charlyday.optimisation.services.algos.RandomSolver;
import crazy.charlyday.optimisation.services.algos.ecj.EcjSolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static crazy.charlyday.optimisation.interfaces.Solvers.*;

@RestController
@RequestMapping("/affectations")
public class DatingController {
    ScoreCalculator scoreCalculator;

    @Autowired
    public DatingController(ScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }

    @Operation(summary = "Get dating solution", description = "Get dating solution with a given dating problem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @CrossOrigin
    @PostMapping
    public DatingSolutionDto getDatingSolution(@RequestBody DatingProblemDto datingProblemDto) {
        return DatingSolutionMapper.INSTANCE.mapToDTO(scoreCalculator.compute(DatingProblemMapper.INSTANCE.mapToEntity(datingProblemDto)));
    }

    @CrossOrigin
    @PostMapping(value = {"/glouton/{timeout}", "/glouton"})
    public DatingSolutionDto getDatingSolutionGlouton(@RequestBody DatingProblemDto datingProblemDto, @PathVariable(required = false) Integer timeout) {
        return getSolution(datingProblemDto, timeout, GLOUTON);
    }

    @CrossOrigin
    @PostMapping(value = {"/random/{timeout}", "/random"})
    public DatingSolutionDto getDatingSolutionRandom(@RequestBody DatingProblemDto datingProblemDto, @PathVariable(required = false) Integer timeout) {
        return getSolution(datingProblemDto, timeout, RANDOM);
    }

    @CrossOrigin
    @PostMapping(value = {"/evolution/{timeout}", "/evolution"})
    public DatingSolutionDto getDatingSolutionEvolution(@RequestBody DatingProblemDto datingProblemDto, @PathVariable(required = false) Integer timeout) {
        return getSolution(datingProblemDto, timeout, EVOLUTION);
    }

    public DatingSolutionDto getSolution(DatingProblemDto datingProblemDto, Integer timeout, Solvers solver){
        if (timeout == null) {
            timeout = 200;
        }

        Solver actualSolver = switch (solver) {
            case GLOUTON -> new GloutonSolver(timeout);
            case RANDOM -> new RandomSolver(timeout);
            case EVOLUTION -> new EcjSolver(timeout);
        };

        ScoreCalculator calculator = new ScoreCalculator(actualSolver);
        return DatingSolutionMapper.INSTANCE.mapToDTO(calculator.compute(DatingProblemMapper.INSTANCE.mapToEntity(datingProblemDto)));
    }
}
