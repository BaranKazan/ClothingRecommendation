package com.handm.assessment.recommendation;

import com.handm.assessment.product.Clothe.*;
import com.handm.assessment.product.Product;
import com.handm.assessment.product.Tag;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    private final List<Product> products = new ArrayList<>();
    Random random = new Random();

    /**
     * This construction initiates a method that will generate n amount of products.
     */
    public RecommendationService() {
        GenerateData(200);
    }

    /**
     * This method generates fake data that is required to have some clothing to recommend.
     *
     * @param numberOfData Given amount to generate amount of product.
     */
    public void GenerateData(int numberOfData) {
        //Creating different namings
        List<String> hatList = List.of("Cowboy Hat", "Baseball Cap", "Bowler", "Bucket Hat");
        List<String> shirtList = List.of("Turtleneck", "T-Shirt", "Hoody", "Jacket");
        List<String> pantsList = List.of("Jeans", "Shorts", "Joggers", "Chinos");
        List<String> shoeList = List.of("Flip Flops", "Sneaker", "Sandals", "Chelsea Boots");
        List<String> sizeList = List.of("S", "M", "L", "XL");
        List<String> colorList = List.of("Blue", "Red", "Green", "Orange");
        List<String> accessoryList = List.of("Watch", "Belt", "Necklace", "Bracelets");
        // Creating a list of different instances that has same parent.
        List<Class<? extends Product>> productTypes = List.of(Hat.class, Shirt.class, Pants.class, Shoes.class,
                Accessory.class
        );

        //Loop that generates data each iteration.
        for (int i = 0; i < numberOfData; i++) {
            String size = sizeList.get(random.nextInt(sizeList.size()));
            String color = colorList.get(random.nextInt(colorList.size()));
            int price = random.nextInt(80) + 20; //Setting the price range between 20 and 100.
            //Uses the previous attribute productTypes to randomly select an instance.
            Class<? extends Product> productType = productTypes.get(random.nextInt(productTypes.size()));

            int numberOfTags = random.nextInt(Tag.values().length) + 1;
            //Using Java stream to bring given amount of tags. Each are unique.
            List<Tag> tags = random
                    .ints(0, Tag.values().length)
                    .distinct()
                    .limit(numberOfTags)
                    .mapToObj(j -> Tag.values()[j])
                    .toList();

            String name = null;
            Product product = null;
            //The if statement checks the instance of the data and creates a type of Product with given instance.
            if (productType.equals(Hat.class)) {
                name = hatList.get(random.nextInt(hatList.size()));
                product = new Hat(i, name, price, size, tags);
            } else if (productType.equals(Shirt.class)) {
                name = shirtList.get(random.nextInt(shirtList.size()));
                product = new Shirt(i, name, price, size, tags);
            } else if (productType.equals(Pants.class)) {
                name = pantsList.get(random.nextInt(pantsList.size()));
                product = new Pants(i, name, price, size, tags);
            } else if (productType.equals(Shoes.class)) {
                name = shoeList.get(random.nextInt(shoeList.size()));
                product = new Shoes(i, name, price, size, tags);
            } else {
                name = accessoryList.get(random.nextInt(accessoryList.size()));
                product = new Accessory(i, name, price, size, tags);
            }
            products.add(product);
        }

    }

    /**
     * This is where the magic happens. It will first get all necessary parameter to be able to recommend a set.
     * If the Budget is too little, it will only return some of the set such as shirt and pants.
     * @param recommendation It receives the instance of Recommendation to get necessary data to work with.
     * @return It will return recommended set for clothing. A set includes hat, shirt, pants, shoes and accessory.
     */
    public List<Product> getRecommendation(Recommendation recommendation) {
        Set<Tag> recommendedTags = new LinkedHashSet<>();
        /* It will automatically as preference as Casual and Sporty if a event is Birthday.
        But when it is a special occasion such as wedding, it will add Elegant and Classic as preference. */
        if (recommendation.getEvent() == Event.Birthday) {
            recommendedTags.add(Tag.Casual);
            recommendedTags.add(Tag.Sporty);
        } else {
            recommendedTags.add(Tag.Elegant);
            recommendedTags.add(Tag.Classic);
        }
        recommendedTags.addAll(recommendation.getPreference());

        List<Product> shuffledProducts = new ArrayList<>(products);
        Collections.shuffle(shuffledProducts);

        int budget = recommendation.getBudget();

        return createSet(shuffledProducts, recommendedTags, budget);
    }

    /**
     * The method generates a set that is recommended. The recommendation is depended on preference, price.
     * @param products The list of products that will be picked depending on price and preference.
     * @param tags It is required to figure out the preference
     * @param budget It is required to recommend a set under the given budget price
     * @return Return a recommended set of clothing
     */
    public List<Product> createSet(List<Product> products, Set<Tag> tags, int budget) {
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
        if (selectedHat != null) {
            selectedProducts.add(selectedHat);
            selectedProducts.add(selectedShirt);
            selectedProducts.add(selectedPants);
            selectedProducts.add(selectedShoes);
            selectedProducts.add(selectedAccessory);
        }
        return selectedProducts;
    }

    /**
     * This method checks if the product matches with the given list of tag.
     * @param product The product that will be checked
     * @param tags The list of tags to check if at least one matches.
     * @return Return true if it has at least one matching tag, else it will return false.
     */
    public boolean matchProduct(Product product, Set<Tag> tags) {
        for (Tag tag : tags) {
            if (product.getTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }

}
