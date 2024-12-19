package com.example.palheiro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.palheiro.adaptadores.EncomendaAdaptador;
import com.example.palheiro.adaptadores.ListaLivrosAdaptador;
import com.example.palheiro.adaptadores.ListasComprasAdaptador;
import com.example.palheiro.listeners.EncomendasListener;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class EncomendasFragment extends Fragment implements EncomendasListener {

    private ListView lvEncomendas;

    public EncomendasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_encomendas, container, false);

        setHasOptionsMenu(true);

        lvEncomendas = view.findViewById(R.id.lvEncomendas);

        //set the listener
        SingletonPalheiro.getInstance(getContext()).setEncomendasListener(this);

        //fazer pedido a API de maneira assicrona
        SingletonPalheiro.getInstance(getContext()).getAllEncomendasAPI(getContext());

        //apos pedido feito com sucesso vamos executar o onRefresh method com o listener automaticamente

        return view;

    }

    @Override
    public void onRefreshEncomendas(ArrayList<Fatura> encomendas)
    {
        if(encomendas != null)
            lvEncomendas.setAdapter(new EncomendaAdaptador(getContext(), encomendas));
    }
}