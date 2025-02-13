package crazy.charlyday.optimisation.controllers;

import crazy.charlyday.optimisation.dtos.DatingProblemDto;
import crazy.charlyday.optimisation.dtos.DatingSolutionDto;
import crazy.charlyday.optimisation.mappers.DatingProblemMapper;
import crazy.charlyday.optimisation.mappers.DatingSolutionMapper;
import crazy.charlyday.optimisation.services.ScoreCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dating")
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
    @PostMapping
    public DatingSolutionDto getDatingSolution(@RequestBody DatingProblemDto datingProblemDto) {
        return DatingSolutionMapper.INSTANCE.mapToDTO(scoreCalculator.compute(DatingProblemMapper.INSTANCE.mapToEntity(datingProblemDto)));
    }
}
