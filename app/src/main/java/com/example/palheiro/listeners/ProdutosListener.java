package com.example.palheiro.listeners;

import com.example.palheiro.modelo.Produto;

import java.util.ArrayList;

public interface ProdutosListener
{
    void onRefreshDetalhes(ArrayList<Produto> listaProdutos);
}
