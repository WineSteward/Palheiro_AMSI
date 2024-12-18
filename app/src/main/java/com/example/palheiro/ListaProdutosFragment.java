package com.example.palheiro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.palheiro.adaptadores.ListaProdutosAdaptador;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;


public class ListaProdutosFragment extends Fragment {

    private ListView lvProdutos;
    private ArrayList<Produto> produtos;

    public ListaProdutosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);

        setHasOptionsMenu(true);

        lvProdutos = getView().findViewById(R.id.lvProdutos);

        SingletonPalheiro.getInstance(getContext()).getAllProdutosAPI(getContext());
        produtos = SingletonPalheiro.getInstance(getContext()).getProdutos(); //traz o array de livros pelo Singleton

        lvProdutos.setAdapter(new ListaProdutosAdaptador(getContext(), produtos));


        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {//clicar num produto
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), DetalhesProdutoActivity.class);
                intent.putExtra(DetalhesProdutoActivity.ID, (int) id);
                startActivity(intent);
            }
        });

        return view;
    }
}