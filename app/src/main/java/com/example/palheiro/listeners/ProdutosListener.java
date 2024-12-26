package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.Categoria;
import com.example.palheiro.modelo.Produto;

import java.util.ArrayList;

public interface ProdutosListener
{
    void onRefreshProdutos(ArrayList<Produto> produtos);
    void onRefreshCategorias(ArrayList<Categoria> categorias, Context context);
    void onProdutoAddCarrinho(Context context);
}
