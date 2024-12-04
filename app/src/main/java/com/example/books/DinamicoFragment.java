package com.example.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;


public class DinamicoFragment extends Fragment {

    private TextView tvTitulo, tvAno, tvSerie, tvAutor;
    private ImageView imageCapa;


    public DinamicoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dinamico, container, false);

        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvAutor = view.findViewById(R.id.tvAutor);
        tvSerie = view.findViewById(R.id.tvSerie);
        tvAno = view.findViewById(R.id.tvAno);
        imageCapa = view.findViewById(R.id.imageView2);

        carregarLivro();

        return view;
    }

    private void carregarLivro()
    {

        if(SingletonGestorLivros.getInstance(getContext()).getLivrosBD().isEmpty())
            return;

        Livro livro = SingletonGestorLivros.getInstance(getContext()).getLivrosBD().get(0);

        tvTitulo.setText(livro.getTitulo());
        tvAutor.setText(livro.getAutor());
        tvSerie.setText(livro.getSerie());
        tvAno.setText(livro.getAno()+""); //concatenar uma empty string para transform int em string
        imageCapa.setImageResource(livro.getCapa());


    }
}

