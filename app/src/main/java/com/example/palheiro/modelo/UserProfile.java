package com.example.palheiro.modelo;

public class UserProfile
{
    private int id;
    private String nif, morada, morada2, codigoPostal, username, email;

    public UserProfile(int id, String nif, String morada, String morada2, String codigoPostal, String username, String email) {
        this.id = id;
        this.nif = nif;
        this.morada = morada;
        this.morada2 = morada2;
        this.codigoPostal = codigoPostal;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getMorada2() {
        return morada2;
    }

    public void setMorada2(String morada2) {
        this.morada2 = morada2;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
