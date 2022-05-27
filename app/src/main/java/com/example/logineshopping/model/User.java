package com.example.logineshopping.model;

public class User {
    public String nume, email, adresa, numar_telefon, admin;

    public User() {

    }

    public User(String nume, String email, String adresa, String numar_telefon, String admin) {
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
        this.numar_telefon = numar_telefon;
        this.admin = admin;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return numar_telefon;
    }

    public void setTelefon(String numar_telefon) {
        this.numar_telefon = numar_telefon;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
