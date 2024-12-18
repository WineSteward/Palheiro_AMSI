package com.example.palheiro.modelo;

public class Iva
{
    private int id, valorPorcentagem;

    public Iva(int id, int valorPorcentagem) {
        this.id = id;
        this.valorPorcentagem = valorPorcentagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValorPorcentagem() {
        return valorPorcentagem;
    }

    public void setValorPorcentagem(int valorPorcentagem) {
        this.valorPorcentagem = valorPorcentagem;
    }
}
