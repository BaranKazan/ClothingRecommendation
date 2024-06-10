package com.handm.assessment.recommendation;

import com.handm.assessment.product.Clothe.*;
import com.handm.assessment.product.Product;
import com.handm.assessment.product.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductGenerator {

    private final Random RANDOM = new Random();
    private final int MINIMUM_PRICE = 20;
    private final int MAXIMUM_PRICE = 100;

    /**
     * This method generates fake data that is required to have some clothing to recommend.
     * @param numberOfData Given amount to generate amount of product.
     */
    public List<Product> generateData(int numberOfData) {
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

        List<Product> products = new ArrayList<>();
        //Loop that generates data each iteration.
        for (int i = 0; i < numberOfData; i++) {
            String size = sizeList.get(RANDOM.nextInt(sizeList.size()));
            String color = colorList.get(RANDOM.nextInt(colorList.size()));
            //Setting the price range between 20 and 100.
            int price = RANDOM.nextInt(MAXIMUM_PRICE-MINIMUM_PRICE) + MINIMUM_PRICE;
            //Uses the previous attribute productTypes to randomly select an instance.
            Class<? extends Product> productType = productTypes.get(RANDOM.nextInt(productTypes.size()));

            int numberOfTags = RANDOM.nextInt(Tag.values().length) + 1;
            //Using Java stream to bring given amount of tags. Each are unique.
            List<Tag> tags = RANDOM
                    .ints(0, Tag.values().length)
                    .distinct()
                    .limit(numberOfTags)
                    .mapToObj(j -> Tag.values()[j])
                    .toList();

            String name = null;
            Product product = null;
            //The if statement checks the instance of the data and creates a type of Product with given instance.
            if (productType.equals(Hat.class)) {
                name = hatList.get(RANDOM.nextInt(hatList.size()));
                product = new Hat(i, name, price, size, tags);
            } else if (productType.equals(Shirt.class)) {
                name = shirtList.get(RANDOM.nextInt(shirtList.size()));
                product = new Shirt(i, name, price, size, tags);
            } else if (productType.equals(Pants.class)) {
                name = pantsList.get(RANDOM.nextInt(pantsList.size()));
                product = new Pants(i, name, price, size, tags);
            } else if (productType.equals(Shoes.class)) {
                name = shoeList.get(RANDOM.nextInt(shoeList.size()));
                product = new Shoes(i, name, price, size, tags);
            } else if (productType.equals(Accessory.class)) {
                name = accessoryList.get(RANDOM.nextInt(accessoryList.size()));
                product = new Accessory(i, name, price, size, tags);
            }
            if (product != null)
                products.add(product);
        }
        return products;
    }
}
