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
    private ListasComprasAdaptador adapter;
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
        fabLista = view.findViewById(R.id.fabLista);

        listasCompras = new ArrayList<>();
        adapter = new ListasComprasAdaptador(getContext(), listasCompras);
        lvListasCompras.setAdapter(adapter);

        SingletonPalheiro.getInstance(getContext()).setListasComprasListener(this);

        SingletonPalheiro.getInstance(getContext()).getAllListasComprasAPI(getContext());

        lvListasCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesListaComprasActivity.class);
                intent.putExtra(DetalhesListaComprasActivity.ID, (int) id);
                startActivityForResult(intent, EDIT);
            }
        });

        fabLista.setOnClickListener(new View.OnClickListener() { // Click on +
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

        // Refresh ListView
        SingletonPalheiro.getInstance(getContext()).getAllListasComprasAPI(getContext());

        if (requestCode == ADD)
            Toast.makeText(getContext(), "Lista Compras Adicionada com sucesso", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "Operação executada com sucesso", Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onRefreshListasCompras(ArrayList<ListaCompras> listasCompras) {
        if (listasCompras != null) {
            this.listasCompras.clear();
            this.listasCompras.addAll(listasCompras);
            adapter.notifyDataSetChanged();
        }
    }
}
