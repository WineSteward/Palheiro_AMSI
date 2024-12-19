package com.example.palheiro.listeners;

import com.example.palheiro.modelo.LinhaCarrinho;

import java.util.ArrayList;

public interface CarrinhoListener
{
    void onRefreshCarrinho(ArrayList<LinhaCarrinho> linhasCarrinho);
}
