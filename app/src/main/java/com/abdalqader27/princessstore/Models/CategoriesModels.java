package com.abdalqader27.princessstore.Models;

import com.parse.ParseFile;

import java.io.Serializable;

public class CategoriesModels   implements Serializable {
    private String id;
    private String Name;
    private String Price;
    private String ProductsId;
    private ParseFile image;

    public CategoriesModels(String id, String name, String price, String productsId, ParseFile image) {
        this.id = id;
        Name = name;
        Price = price;
        ProductsId = productsId;
        this.image = image;
    }
    public CategoriesModels() {
    }

    public String getProductsId() {
        return ProductsId;
    }

    public void setProductsId(String productsId) {
        ProductsId = productsId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }
}
