package com.example.palheiro.modelo;

import java.util.ArrayList;

public class Fatura
{
    private int id, valida, estadoEncomenda;
    private MetodoExpedicao metodoExpedicao;
    private MetodoPagamento metodoPagamento;
    float total;
    private String data;
    private ArrayList<LinhaFatura> linhasFatura = new ArrayList<>();
    private Desconto desconto;

    public Fatura(int id, int valida, int estadoEncomenda, MetodoExpedicao metodoExpedicao, MetodoPagamento metodoPagamento, float total, String data, ArrayList<LinhaFatura> linhasFatura, Desconto desconto)
    {
        this.id = id;
        this.valida = valida;
        this.estadoEncomenda = estadoEncomenda;
        this.metodoExpedicao = metodoExpedicao;
        this.metodoPagamento = metodoPagamento;
        this.total = total;
        this.data = data;
        this.linhasFatura = linhasFatura;
        this.desconto = desconto;
    }

    public Fatura(int id, int estadoEncomenda, String data)
    {
        this.id = id;
        this.estadoEncomenda = estadoEncomenda;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValida() {
        return valida;
    }

    public void setValida(int valida) {
        this.valida = valida;
    }

    public int getEstadoEncomenda() {
        return estadoEncomenda;
    }

    public void setEstadoEncomenda(int estadoEncomenda) {
        this.estadoEncomenda = estadoEncomenda;
    }

    public MetodoExpedicao getMetodoExpedicao() {
        return metodoExpedicao;
    }

    public void setMetodoExpedicao(MetodoExpedicao metodoExpedicao) {
        this.metodoExpedicao = metodoExpedicao;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<LinhaFatura> getLinhasFatura() {
        return linhasFatura;
    }

    public void setLinhasFatura(ArrayList<LinhaFatura> linhasFatura) {
        this.linhasFatura = linhasFatura;
    }

    public Desconto getDesconto() {
        return desconto;
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }
}
