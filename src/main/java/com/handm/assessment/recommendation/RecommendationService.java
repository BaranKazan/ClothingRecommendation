package com.handm.assessment.recommendation;

import com.handm.assessment.exception.BadRequestException;
import com.handm.assessment.product.Clothe.*;
import com.handm.assessment.product.Product;
import com.handm.assessment.product.Tag;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    private final List<Product> PRODUCTS = new ArrayList<>();
    private final int GENERATE_DATA = 500;
    /**
     * This construction initiates a method that will generate n amount of products.
     */
    public RecommendationService() {
        ProductGenerator productGenerator = new ProductGenerator();
        PRODUCTS.addAll(productGenerator.generateData(GENERATE_DATA));
    }

    /**
     * This is where the magic happens. It will first get all necessary parameter to be able to recommend a set.
     * If the Budget is too little, it will only return some of the set such as shirt and pants.
     * @param recommendation It receives the instance of Recommendation to get necessary data to work with.
     * @return It will return recommended set for clothing. A set includes hat, shirt, pants, shoes and accessory.
     */
    public List<Product> getRecommendation(Recommendation recommendation) {
        Set<Tag> recommendedTags = new LinkedHashSet<>();
        /* It will automatically add preference as Casual during Birthday event.
        But when it is a special occasion such as wedding, it will add Elegant and Classic as preference. */
        if (recommendation.getEvent() == Event.Birthday) {
            recommendedTags.add(Tag.Casual);
        } else if (recommendation.getEvent() == Event.Wedding) {
            recommendedTags.add(Tag.Elegant);
            recommendedTags.add(Tag.Classic);
        } else {
            throw new BadRequestException("Inout of the Event is required.");
        }
        recommendedTags.addAll(recommendation.getPreference());

        List<Product> shuffledProducts = new ArrayList<>(PRODUCTS);
        Collections.shuffle(shuffledProducts);

        int budget = recommendation.getBudget();

        return generateSet(shuffledProducts, recommendedTags, budget);
    }

    /**
     * The method generates a set that is recommended. The recommendation is depended on preference, price.
     * @param products The list of products that will be picked depending on price and preference.
     * @param tags It is required to figure out the preference
     * @param budget It is required to recommend a set under the given budget price
     * @return Return a recommended set of clothing
     */
    public List<Product> generateSet(List<Product> products, Set<Tag> tags, int budget) {
        //Creating empty instance of each instance that has Product as their parent.
        Product selectedHat = null;
        Product selectedShoes = null;
        Product selectedShirt = null;
        Product selectedPants = null;
        Product selectedAccessory = null;

        int totalPrice = 0;

        for (Product product : products) {
            //Checks if at least one tag matches with the Product
            if (matchProduct(product, tags)) {
                //Creating a boolean attribute to check if it is still under the budget
                boolean affordable = totalPrice + product.getPrice() <= budget;
                if (product instanceof Hat && selectedHat == null && affordable) {
                    selectedHat = product;
                    totalPrice += product.getPrice();
                } else if (product instanceof Shoes && selectedShoes == null && affordable) {
                    selectedShoes = product;
                    totalPrice += product.getPrice();
                } else if (product instanceof Shirt && selectedShirt == null && affordable) {
                    selectedShirt = product;
                    totalPrice += product.getPrice();
                } else if (product instanceof Pants && selectedPants == null && affordable) {
                    selectedPants = product;
                    totalPrice += product.getPrice();
                } else if (product instanceof Accessory && selectedAccessory == null && affordable) {
                    selectedAccessory = product;
                    totalPrice += product.getPrice();
                }
                //This will break the loop if all the attributes are filled with data
                if (selectedHat != null && selectedShoes != null && selectedShirt != null &&
                        selectedPants != null && selectedAccessory != null) {
                    break;
                }
            }
        }
        List<Product> selectedProducts = new ArrayList<>();
        if (selectedHat != null ) {
            selectedProducts.add(selectedHat);
        }
        if(selectedShirt != null) {
            selectedProducts.add(selectedShirt);
        }
        if(selectedPants != null) {
            selectedProducts.add(selectedPants);
        }
        if(selectedShoes != null) {
            selectedProducts.add(selectedShoes);
        }
        if(selectedAccessory != null) {
            selectedProducts.add(selectedAccessory);
        }
        return selectedProducts;
    }

    /**
     * This method checks if the product matches with all the tags inside the Set.
     * @param product The product that will be checked
     * @param tags The list of tags to check if at least one matches.
     * @return Return true if it has at least one matching tag, else it will return false.
     */
    public boolean matchProduct(Product product, Set<Tag> tags) {
        for (Tag tag : tags) {
            if (!product.getTags().contains(tag)) {
                return false;
            }
        }
        return true;
    }

}
