package com.abdalqader27.princessstore.Models;

import java.io.Serializable;

public class Notificons_models implements Serializable {
    private String title;
    private String message;

    public Notificons_models(String title, String message) {
        this.title = title;
        this.message = message;
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
