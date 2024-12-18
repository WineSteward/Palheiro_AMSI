package com.example.palheiro.modelo;

public class Imagem
{
    private int id;
    private String ficheiro;

    public Imagem(int id, String ficheiro) {
        this.id = id;
        this.ficheiro = ficheiro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFicheiro()
    {
        return ficheiro;
    }

    public void setFicheiro(String ficheiro)
    {
        this.ficheiro = ficheiro;
    }
}
