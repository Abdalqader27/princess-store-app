package com.abdalqader27.princessstore.Models;

import com.parse.ParseFile;

public class CarouselModels {
    private String id;
    private String title;
    private String description;
    private ParseFile image;

    public CarouselModels(String id, String title, String description, ParseFile image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }
}
