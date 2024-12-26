package com.example.palheiro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.palheiro.adaptadores.ListaFaturasAdaptador;
import com.example.palheiro.adaptadores.ListaProdutosAdaptador;
import com.example.palheiro.listeners.FaturasListener;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;


public class FaturasFragment extends Fragment implements FaturasListener {

    private ListView lvFaturas;
    private ArrayList<Fatura> faturas;

    public FaturasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faturas, container, false);

        lvFaturas = view.findViewById(R.id.lvFaturas);

        SingletonPalheiro.getInstance(getContext()).setFaturasListener(this);

        SingletonPalheiro.getInstance(getContext()).getAllFaturasAPI(getContext());

        lvFaturas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesFaturaActivity.class);
                intent.putExtra(DetalhesFaturaActivity.ID, (int) id);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onRefreshFaturas(ArrayList<Fatura> faturas) {
        if(faturas != null)
        {
            lvFaturas.setAdapter(new ListaFaturasAdaptador(getContext(), faturas));
        }
    }
}