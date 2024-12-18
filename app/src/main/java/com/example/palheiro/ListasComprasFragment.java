package com.example.palheiro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.palheiro.adaptadores.ListasComprasAdaptador;
import com.example.palheiro.listeners.ListasComprasListener;
import com.example.palheiro.modelo.ListaCompras;
import com.example.palheiro.modelo.SingletonPalheiro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListasComprasFragment extends Fragment implements ListasComprasListener {

    private ListView lvListasCompras;
    private ArrayList<ListaCompras> listasCompras;
    private FloatingActionButton fabLista;
    public static final int ADD = 100;
    public static final int EDIT = 200;
    public static final int DELETE = 300;


    public ListasComprasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listas_compras, container, false);


        setHasOptionsMenu(true);

        lvListasCompras = view.findViewById(R.id.lvlistaCompras);

        //registar no singleton para obter resposta
        SingletonPalheiro.getInstance(getContext()).setListasComprasListener(this);

        //fazer o pedido ao singleton, garante que é assincrono
        SingletonPalheiro.getInstance(getContext()).getAllListasComprasAPI(getContext());

        lvListasCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesListaComprasActivity.class);
                intent.putExtra(DetalhesListaComprasActivity.ID, (int) id);
                startActivityForResult(intent, EDIT);
            }
        });


        fabLista = view.findViewById(R.id.fabLista);

        fabLista.setOnClickListener(new View.OnClickListener() { //clicar no +
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesListaComprasActivity.class);
                startActivityForResult(intent, ADD);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //listView Atualizada
        SingletonPalheiro.getInstance(getContext()).getAllListasComprasAPI(getContext());

        if(requestCode == ADD)
            Toast.makeText(getContext(), "Lista Compras Adicionada com sucesso", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "Lista Compras Editada com sucesso", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRefreshListasCompras(ArrayList<ListaCompras> listasCompras) {
        if(listasCompras != null)
            lvListasCompras.setAdapter(new ListasComprasAdaptador(getContext(), listasCompras));
    }
}