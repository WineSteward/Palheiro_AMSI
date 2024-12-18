package com.example.palheiro.modelo;

import java.util.ArrayList;

public class Carrinho
{
    private int id;
    private float total;
    private ArrayList<LinhaCarrinho> linhasCarrinho = new ArrayList<>();

    public Carrinho(int id, float total, ArrayList<LinhaCarrinho> linhasCarrinho)
    {
        this.id = id;
        this.total = total;
        this.linhasCarrinho = linhasCarrinho;
    }

    public ArrayList<LinhaCarrinho> getLinhasCarrinho() {
        return new ArrayList<>(linhasCarrinho);
    }

    public void setLinhasCarrinho(ArrayList<LinhaCarrinho> linhasCarrinho) {
        this.linhasCarrinho = linhasCarrinho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
