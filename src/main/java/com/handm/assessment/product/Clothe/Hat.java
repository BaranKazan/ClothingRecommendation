package com.handm.assessment.product.Clothe;

import com.handm.assessment.product.Product;
import com.handm.assessment.product.Tag;

import java.util.List;

public class Hat extends Product {

    public Hat() {
    }

    public Hat(long id, String name, int price, String size, List<Tag> tags) {
        super(id, name, price, size, tags);
    }
}
