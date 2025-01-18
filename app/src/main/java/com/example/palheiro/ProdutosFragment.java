package com.example.palheiro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palheiro.adaptadores.ListaProdutosAdaptador;
import com.example.palheiro.listeners.ProdutosListener;
import com.example.palheiro.modelo.Categoria;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;


public class ProdutosFragment extends Fragment implements ProdutosListener {

    private ListView lvProdutos;
    private ArrayList<Produto> produtos;
    private Spinner spinnerCategorias;
    private Categoria selectedCategoria;
    private String currentQuery = "";

    public ProdutosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);

        setHasOptionsMenu(true);

        lvProdutos = view.findViewById(R.id.lvProdutos);
        spinnerCategorias = view.findViewById(R.id.spinnerCategoriasListaProdutos);

        // Set the listener for product-related updates
        SingletonPalheiro.getInstance(getContext()).setProdutosListener(this);

        // Fetch all products and categories from the API
        SingletonPalheiro.getInstance(getContext()).getAllProdutosAPI(getContext());
        SingletonPalheiro.getInstance(getContext()).getAllCategoriasAPI(getContext());

        // Set item click listener for the ListView
        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), DetalhesProdutoActivity.class);
                intent.putExtra(DetalhesProdutoActivity.ID, (int) id);
                startActivity(intent);
            }
        });

        // Set up categoria filter
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoria = (Categoria) parent.getItemAtPosition(position);
                filterAndDisplayProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Clear the selected category and filter products
                selectedCategoria = null;
                filterAndDisplayProducts();
            }
        });

        return view;
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
            }// Handle query submission (if needed)

            @Override
            public boolean onQueryTextChange(String query) {
                currentQuery = query.toLowerCase();
                filterAndDisplayProducts();
                return true;
            }
        });
    }

    //method to filter products based on categoria or nome or both
    private void filterAndDisplayProducts() {
        ArrayList<Produto> filteredProdutos = new ArrayList<>();

        for (Produto produto : SingletonPalheiro.getInstance(getContext()).getProdutos()) {

            boolean matchesCategory = selectedCategoria == null
                    || selectedCategoria.getId() == -1
                    || produto.getCategoria().getId() == selectedCategoria.getId();

            // Empty name search, categoria check is sufficient
            if (currentQuery.isEmpty() && matchesCategory) {
                filteredProdutos.add(produto);
                continue;
            }

            // If query exists, check if it matches the produto nome
            boolean matchesQuery = produto.getNome().toLowerCase().contains(currentQuery);


            if (matchesCategory && matchesQuery) {
                filteredProdutos.add(produto);
            }
        }

        //atualizar o adaptador
        lvProdutos.setAdapter(new ListaProdutosAdaptador(getContext(), filteredProdutos));
    }


    @Override
    public void onRefreshProdutos(ArrayList<Produto> produtos) {
        if (produtos != null) {
            this.produtos = produtos;
            filterAndDisplayProducts();
        }
    }

    @Override
    public void onRefreshCategorias(ArrayList<Categoria> categorias, Context context) {
        if (categorias != null) {
            Categoria defaultCategoria = new Categoria(-1, "Selecione uma categoria");
            categorias.add(0, defaultCategoria);

            ArrayAdapter<Categoria> categoriaAdaptador = new ArrayAdapter<Categoria>(context, android.R.layout.simple_spinner_item, categorias) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    // Inflate the dropdown view and customize the text color
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    if (textView != null) {
                        textView.setTextColor(Color.BLACK);  // Set the dropdown item text color
                    }
                    view.setBackgroundColor(Color.WHITE);
                    return view;
                }
            };
            categoriaAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategorias.setAdapter(categoriaAdaptador);
        }
    }

    @Override
    public void onProdutoAddCarrinho(Context context) {
        Toast.makeText(context, "Produto Adicionado ao carrinho com sucesso", Toast.LENGTH_SHORT).show();
    }
}