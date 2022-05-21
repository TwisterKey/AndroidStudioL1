package com.example.logineshopping.model;

public class Produs {
    private String imageUrl;
    private String name;

    public Produs() {

    }

    public Produs(String imageUrl, String name) {
        if (name.trim().equals(""))
            name = "Fara nume";
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
