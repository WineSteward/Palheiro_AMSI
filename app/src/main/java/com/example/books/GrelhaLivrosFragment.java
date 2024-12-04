package com.example.books;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.books.adaptadores.GrelhaLivrosAdaptador;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;

import java.util.ArrayList;

public class GrelhaLivrosFragment extends Fragment {

    private GridView gvLivros;
    private ArrayList<Livro> livros;

    public GrelhaLivrosFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grelha_livros, container, false);

        gvLivros = view.findViewById(R.id.gvLivros);

        livros = SingletonGestorLivros.getInstance(getContext()).getLivrosBD(); //traz o array de livros

        gvLivros.setAdapter(new GrelhaLivrosAdaptador(getContext(), livros));

        gvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id)
            {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID, (int) id);
                startActivity(intent);
            }
        });


        return view;
    }
}