package com.example.palheiro.modelo;

import java.util.Date;

public class LinhaFatura
{
    private int id, quantidade, percentagemIva, produtoId, faturaId;
    private double total, valorUnitario, valorIva, subtotal;
    private boolean isDiscountLine;

    public LinhaFatura(int id, int quantidade, int percentagemIva, double total, double valorUnitario, double valorIva, double subtotal, int faturaId, int produtoId) {
        this.id = id;
        this.quantidade = quantidade;
        this.percentagemIva = percentagemIva;
        this.total = total;
        this.valorUnitario = valorUnitario;
        this.valorIva = valorIva;
        this.subtotal = subtotal;
        this.faturaId = faturaId;
        this.produtoId = produtoId;
    }

    public LinhaFatura(int id, int quantidade, int percentagemIva, double total, double valorUnitario, double valorIva, double subtotal, int faturaId, int produtoId, boolean isDiscountLine) {
        this.id = id;
        this.quantidade = quantidade;
        this.percentagemIva = percentagemIva;
        this.total = total;
        this.valorUnitario = valorUnitario;
        this.valorIva = valorIva;
        this.subtotal = subtotal;
        this.faturaId = faturaId;
        this.produtoId = produtoId;
        this.isDiscountLine = isDiscountLine;
    }

    public boolean isDiscountLine() {
        return isDiscountLine;
    }

    public void setDiscountLine(boolean discountLine) {
        isDiscountLine = discountLine;
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

    public int getPercentagemIva() {
        return percentagemIva;
    }

    public void setPercentagemIva(int percentagemIva) {
        this.percentagemIva = percentagemIva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorIva() {
        return valorIva;
    }

    public void setValorIva(double valorIva) {
        this.valorIva = valorIva;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getFaturaId() {
        return faturaId;
    }

    public void setFaturaId(int faturaId) {
        this.faturaId = faturaId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
}
