package com.example.palheiro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.palheiro.adaptadores.CarrinhoAdaptador;
import com.example.palheiro.listeners.ListaComprasListener;
import com.example.palheiro.listeners.ListasComprasListener;
import com.example.palheiro.modelo.ListaCompras;
import com.example.palheiro.modelo.SingletonPalheiro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetalhesListaComprasActivity extends AppCompatActivity implements ListaComprasListener
{

    public static final String ID = "ID";
    private FloatingActionButton fab;
    private EditText etTitulo, etDescricao;
    private ListaCompras listaCompras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_lista_compras);

        int id = getIntent().getIntExtra(ID, -1);
        listaCompras = SingletonPalheiro.getInstance(getApplicationContext()).getListaCompras(id);

        SingletonPalheiro.getInstance(getApplicationContext()).setListaComprasListener(this);

        etTitulo = findViewById(R.id.etTituloListaComprasDetalhes);
        etDescricao = findViewById(R.id.etDescricaoListaComprasDetalhes);

        fab = findViewById(R.id.fabListaComprasDetalhes);

        if(listaCompras != null)
            carregarListaCompras();
        else
            setTitle("Nova Lista de Compras");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isListaComprasValid())
                {
                    if(listaCompras != null)
                    {
                        listaCompras.setTitulo(etTitulo.getText().toString());
                        listaCompras.setDescricao(etDescricao.getText().toString());

                        SingletonPalheiro.getInstance(getApplicationContext()).editListaComprasAPI(listaCompras, getApplicationContext());
                    }
                    else
                    {
                        listaCompras = new ListaCompras(0, etTitulo.getText().toString(), etDescricao.getText().toString());

                        SingletonPalheiro.getInstance(getApplicationContext()).addListaComprasAPI(listaCompras, getApplicationContext());
                    }
                }
            }
        });
    }

    private boolean isListaComprasValid()
    {
        String titulo = etTitulo.getText().toString();
        String descricao = etDescricao.getText().toString();

        if(titulo.trim().length() <= 3)
            etTitulo.setError("Titulo Inválido");
        if (descricao.trim().length() <= 3)
            etDescricao.setError("Descrição Inválida");

        return true;
    }

    private void carregarListaCompras()
    {
        setTitle("Detalhes: " + listaCompras.getTitulo());

        etTitulo.setText(listaCompras.getTitulo());
        etDescricao.setText(listaCompras.getDescricao());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if(listaCompras != null)
            getMenuInflater().inflate(R.menu.menu_detalhes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.itemRemover){
            dialogRemover();
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover lista compras")
                .setMessage("Pretende remover a lista compras?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonPalheiro.getInstance(getApplicationContext()).deleteListaComprasBD(listaCompras.getId());

                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    @Override
    public void onRefreshListasCompras(int op)
    {
        Intent intent = new Intent();
        setResult(RESULT_OK);
        finish();
    }
}