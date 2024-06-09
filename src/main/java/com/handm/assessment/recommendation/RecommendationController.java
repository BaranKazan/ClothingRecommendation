package com.handm.assessment.recommendation;

import com.handm.assessment.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Creating a construct that is depending on the service model. It receives parameter for dependency injection.
     * @param recommendationService
     */
    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * POST request for user to receive recommendations.
     * @param recommendation Needs recommendation instance of data such as kind of event, the budget and
     *                       if there are any preference that the user would like to have.
     * @return Returns a list of clothing that is recommended for the occasion.
     * The generating depends on the event and preference, price as well.
     */
    @PostMapping
    public List<Product> getRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.getRecommendation(recommendation);
    }
}
