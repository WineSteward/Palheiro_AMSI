package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.MetodoExpedicao;
import com.example.palheiro.modelo.MetodoPagamento;

import java.util.ArrayList;

public interface CarrinhoListener
{
    void onRefreshCarrinho(ArrayList<LinhaCarrinho> linhasCarrinho);
    void onRefreshMetodosPagamento(ArrayList<MetodoPagamento> metodosPagamento, Context context);
    void onRefreshMetodosExpedicao(ArrayList<MetodoExpedicao> metodosExpedicao, Context context);
    void onCupaoResponse(boolean isValid, double value, Context context);
    void onCompraConcluida(Context context, boolean cupaoValid);
    void onRefreshQuantidade(Context context, int quantidade, double total, LinhaCarrinho linhaCarrinho);
}
