package com.example.books;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesLivroActivity extends AppCompatActivity {

    public static final String ID = "ID";
    private FloatingActionButton fabDetalhes;
    private ImageView imgCapaDetalhes;
    private EditText etTitulo, etAutor, etSerie, etAno;
    private Livro livro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livro);

        int id = getIntent().getIntExtra(ID, -1); //ir buscar o conteudo da const ID e dar um default value
        livro = SingletonGestorLivros.getInstance(getApplicationContext()).getLivro(id);


        imgCapaDetalhes = findViewById(R.id.imgCapaDetalhes);
        etTitulo = findViewById(R.id.etTitulo);
        etSerie = findViewById(R.id.etSerie);
        etAutor = findViewById(R.id.etAutor);
        etAno = findViewById(R.id.etAno);
        fabDetalhes = findViewById(R.id.fabDetalhes);

        if (livro != null) {
            carregarLivro();
        } else
            setTitle("Novo Livro");

        fabDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se vim do click de um item da lista
                if (isLivroValid())//editar livro
                {
                    if (livro != null) {
                        livro.setTitulo(etTitulo.getText().toString());
                        livro.setSerie(etSerie.getText().toString());
                        livro.setAutor(etAutor.getText().toString());
                        livro.setAno(Integer.parseInt(etAno.getText().toString()));

                        SingletonGestorLivros.getInstance(getApplicationContext()).editarLivroBD(livro);

                    }
                    else // --> adicionar livro
                    {
                        livro = new Livro(0,
                                R.drawable.programarandroid2,
                                Integer.parseInt(etAno.getText().toString()),
                                etTitulo.getText().toString(),
                                etSerie.getText().toString(),
                                etAutor.getText().toString());

                        SingletonGestorLivros.getInstance(getApplicationContext()).adicionarLivroBD(livro);

                    }
                    setResult(Activity.RESULT_OK);

                    finish();
                }

                //se vim do click no floatingBtn --> criarLivro
            }
        });


    }

    private boolean isLivroValid() {

        String titulo = etTitulo.getText().toString();
        String serie = etSerie.getText().toString();
        String autor = etAutor.getText().toString();
        String ano = etAno.getText().toString();

        if (titulo.trim().length() <= 3)
            etTitulo.setError("Titulo invalido");
        if (serie.trim().length() <= 3)
            etSerie.setError("Serie invalido");
        if (autor.trim().length() <= 3)
            etAutor.setError("Autor invalido");
        if (ano.length() != 4)
            etAno.setError("Ano invalido");


        return true;
    }

    private void carregarLivro() {
        setTitle("Detalhes:" + livro.getTitulo());
        imgCapaDetalhes.setImageResource(livro.getCapa());
        etTitulo.setText(livro.getTitulo());
        etSerie.setText(livro.getSerie());
        etAutor.setText(livro.getAutor());
        etAno.setText(livro.getAno() + "");
    }

    //carregar o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (livro != null) {
            getMenuInflater().inflate(R.menu.menu_detalhes, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    //gerir os diferentes itens do menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.itemRemover) {
            dialogRemover();
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover livro")
                .setMessage("Pretende remover livro?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonGestorLivros.getInstance(getApplicationContext()).removerLivroBD(livro.getId());

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
}
