package com.example.palheiro.modelo;

public class LinhaCarrinho
{
    private float precoUnitario, total;
    private int quantidade, id;
    private Produto produto;

    public LinhaCarrinho(int id, float precoUnitario, float total, int quantidade,Produto produto) {
        this.precoUnitario = precoUnitario;
        this.total = total;
        this.quantidade = quantidade;
        this.id = id;
        this.produto = produto;
    }

    public float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}



