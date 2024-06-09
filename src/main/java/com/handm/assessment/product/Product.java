package com.handm.assessment.product;

import java.util.List;

public class Product {

    private long id;
    private String name;
    private int price;
    private String size;
    private List<Tag> tags;

    public Product() {
    }

    public Product(long id, String name, int price, String size, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
