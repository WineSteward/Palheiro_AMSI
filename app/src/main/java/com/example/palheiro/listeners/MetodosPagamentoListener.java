package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.MetodoPagamento;

import java.util.ArrayList;

public interface MetodosPagamentoListener
{
    void onRefreshMetodosPagamento(ArrayList<MetodoPagamento> metodosPagamento, Context context);
}
