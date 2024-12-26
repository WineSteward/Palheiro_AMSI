package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.Categoria;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.Produto;

import java.util.ArrayList;

public interface FaturasListener
{
    void onRefreshFaturas(ArrayList<Fatura> faturas);
}
