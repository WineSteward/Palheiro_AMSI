package com.example.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.books.adaptadores.ListaLivrosAdaptador;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListaLivrosFragment extends Fragment {

    private ListView lvLivros;
    private ArrayList<Livro> livros;
    private FloatingActionButton fabLista;
    public static final int ADD = 100;
    public static final int EDIT = 200;

    public ListaLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_lista_livros, container, false);

        setHasOptionsMenu(true);

        lvLivros = view.findViewById(R.id.lvLivros);

        livros = SingletonGestorLivros.getInstance(getContext()).getLivrosBD(); //traz o array de livros

        lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), livros));

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {//clicar no item da lista
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id)
            {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                intent.putExtra(DetalhesLivroActivity.ID, (int) id);
                //startActivity(intent);
                startActivityForResult(intent, EDIT);
            }
        });

        fabLista = view.findViewById(R.id.fabLista);

        fabLista.setOnClickListener(new View.OnClickListener() {// clicar no FAB (+)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                startActivityForResult(intent, ADD);
                //startActivity(intent);
            }
        });


        return view;
    }

    //requestCode = codigo enviado no startActivityForResult
    //resultCode = codigo proveniente da Acitivy/Fragment que foi inicidada pelo startActivityforResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {
            //Algo aconteceu, temos de atualizar a lista

            //variavel do modelo NESTE fragment atualizada
            livros = SingletonGestorLivros.getInstance(getContext()).getLivrosBD();

            //listView atualizada
            lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), livros));

            if(requestCode == ADD)
                Toast.makeText(getContext(), "Livro adicionado com sucesso", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), "Livro editado com sucesso", Toast.LENGTH_LONG).show();
        }
        //nao se fez nada, faz-se nada
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        SearchView searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<Livro> tempLivros = new ArrayList<>();

                for (Livro livro : SingletonGestorLivros.getInstance(getContext()).getLivrosBD())
                {
                    if(livro.getTitulo().toLowerCase().contains(query.toLowerCase()))
                        tempLivros.add(livro);
                }

                lvLivros.setAdapter(new ListaLivrosAdaptador(getContext(), tempLivros));

                return true;
            }
        });
    }
}








