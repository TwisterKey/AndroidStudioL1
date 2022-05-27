package com.example.logineshopping.model;

public class Produs {
    private String imageUrl;
    private String name;
    private String descriere;
    private String pret;

    public Produs() {

    }

    public Produs(String imageUrl, String name, String descriere, String pret) {
        if (name.trim().equals(""))
            name = "Fara nume";
        this.imageUrl = imageUrl;
        this.name = name;
        this.descriere = descriere;
        this.pret = pret;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }
}
