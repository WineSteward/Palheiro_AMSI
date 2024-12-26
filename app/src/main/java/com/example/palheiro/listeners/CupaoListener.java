package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.Desconto;

import java.util.ArrayList;

public interface CupaoListener
{
    void onRefreshCupoes(Context context, ArrayList<Desconto> cupoes);
}
