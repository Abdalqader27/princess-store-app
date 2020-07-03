package com.abdalqader27.princessstore.Models;

import com.parse.ParseFile;

import java.io.Serializable;

public class ItemsModels   implements Serializable {
    private String id;
    private String Name;
    private String Price;
    private String categoriesID;
    private ParseFile image;

    public ItemsModels(String id, String name, String price, String categoriesID, ParseFile image) {
        this.id = id;
        Name = name;
        Price = price;
        this.categoriesID = categoriesID;
        this.image = image;
    }    public ItemsModels() {
    }

    public ItemsModels(String name, String price, ParseFile image) {
        Name = name;
        Price = price;
        this.image = image;
    }

    public String getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(String categoriesID) {
        this.categoriesID = categoriesID;
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
