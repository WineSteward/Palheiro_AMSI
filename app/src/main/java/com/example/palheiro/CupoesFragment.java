package com.example.palheiro;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.palheiro.adaptadores.CupoesAdaptador;
import com.example.palheiro.listeners.CupaoListener;
import com.example.palheiro.modelo.Desconto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;


public class CupoesFragment extends Fragment implements CupaoListener {

    private ListView lvCupoes;


    public CupoesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cupoes, container, false);

        lvCupoes = view.findViewById(R.id.lvCupoes);

        SingletonPalheiro.getInstance(getContext()).setCupaoListener(this);

        SingletonPalheiro.getInstance(getContext()).getAllCupoesAPI(getContext());

        return view;
    }

    @Override
    public void onRefreshCupoes(Context context, ArrayList<Desconto> cupoes) {
        lvCupoes.setAdapter(new CupoesAdaptador(context, cupoes));
    }
}