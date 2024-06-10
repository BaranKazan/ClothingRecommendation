package com.handm.assessment.recommendation;

import com.handm.assessment.exception.BadRequestException;
import com.handm.assessment.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Creating a construct that is depending on the service model. It receives parameter for dependency injection.
     *
     * @param recommendationService
     */
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * POST request for user to receive recommendations.
     *
     * @param recommendation Needs recommendation instance of data such as kind of event, the budget and
     *                       if there are any preference that the user would like to have.
     * @return Returns a list of clothing that is recommended for the occasion.
     * The generating depends on the event and preference, price as well.
     */
    @Operation(summary = "Get Clothing recommendation for the event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated set of clothing for the event",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "The budget value cannot be negative " +
                    "and event cannot be empty.",
                    content = @Content),})
    @PostMapping
    public List<Product> getRecommendation(@RequestBody Recommendation recommendation) {
        if (recommendation.getBudget() < 0)
            throw new BadRequestException("The budget cannot be a negative value.");
        return recommendationService.getRecommendation(recommendation);
    }
}
