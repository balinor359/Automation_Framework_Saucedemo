package com.saucedemo.objects;

import java.util.ArrayList;

/* This class create Product Object with all constructors , getters and setters */
public class Product {
    /* Initialise variables for name, price and product image src */
    private String name;
    private String imageSrc;
    private String price;
    /* Create new product list to store all products from Homepage */
    public static ArrayList<Product> productList = new ArrayList<>();

    public Product() {

    }

    public Product(String name, String price, String imageSrc) {
        super();
        this.name = name;
        this.price = price;
        this.imageSrc = imageSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return '\'' + name + '\'' +
                '\'' + price + '\'' +
                ", '" + imageSrc + '\'';
    }

}
