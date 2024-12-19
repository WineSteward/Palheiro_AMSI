package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.MetodoExpedicao;

import java.util.ArrayList;

public interface MetodosExpedicaoListener
{
    void onRefreshMetodosExpedicao(ArrayList<MetodoExpedicao> metodosExpedicao, Context context);

}
