package com.example.palheiro.listeners;

import com.example.palheiro.modelo.Fatura;

import java.util.ArrayList;

public interface EncomendasListener
{
    void onRefreshEncomendas(ArrayList<Fatura> encomendas);
}
