package com.example.logineshopping.model;

public class Produs {
    private String imageUrl;
    private String name;
    private String descriere;
    private String locatie;
    private String timp;
    private String id;
    private String latitudine;
    private String longitudine;
    private String idpersoana;

    public Produs() {

    }

    public Produs(String imageUrl, String name, String descriere, String idpersoana, String locatie, String timp, String latitudine, String longitudine) {
        if (name.trim().equals(""))
            name = "Fara nume";
        this.imageUrl = imageUrl;
        this.name = name;
        this.descriere = descriere;
        this.locatie = locatie;
        this.timp = timp;
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.idpersoana  = idpersoana;
    }


    public String getIdpersoana() {
        return idpersoana;
    }

    public void setIdpersoana(String idpersoana) {
        this.idpersoana = idpersoana;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocatie() {
        return locatie;
    }
    public void setLocatie(String locatie){this.locatie=locatie;}

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDescriere(){return descriere;}

    public String getTimp() {
        return timp;
    }

    public void setTimp(String timp) {
        this.timp = timp;
    }
}
