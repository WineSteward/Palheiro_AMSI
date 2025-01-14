package com.example.palheiro.modelo;

import android.widget.ImageView;

import java.util.ArrayList;

public class Produto
{
    private int id, quantidade;
    private Categoria categoria;
    private Marca marca;
    private Iva iva;
    private ValorNutricional valornutricional;
    private float preco;
    private String nome, descricao;
    private ArrayList<Imagem> imagens;

    public Produto(int id, String nome, float preco, int quantidade, ArrayList<Imagem> imagens, Marca marca)
    {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagens = imagens;
        this.marca = marca;
    }

    public Produto(int id, Categoria categoria, Iva iva, Marca marca, ValorNutricional valornutricional, int quantidade, float preco, String nome, String descricao, ArrayList<Imagem> imagens)
    {
        this.id = id;
        this.categoria = categoria;
        this.iva = iva;
        this.marca = marca;
        this.valornutricional = valornutricional;
        this.quantidade = quantidade;
        this.preco = preco;
        this.nome = nome;
        this.descricao = descricao;
        this.imagens = imagens;
    }

    public ArrayList<Imagem> getImagens() {
        return imagens;
    }

    public Imagem getOneImage()
    {
        return imagens.get(0);
    }

    public void setImagens(ArrayList<Imagem> imagens) {
        this.imagens = imagens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Iva getIva() {
        return iva;
    }

    public void setIva(Iva iva) {
        this.iva = iva;
    }

    public ValorNutricional getValornutricional() {
        return valornutricional;
    }

    public void setValornutricional(ValorNutricional valornutricional) {
        this.valornutricional = valornutricional;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
