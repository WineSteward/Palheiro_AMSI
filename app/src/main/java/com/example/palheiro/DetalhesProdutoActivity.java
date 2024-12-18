package com.example.palheiro;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.palheiro.modelo.Livro;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

public class DetalhesProdutoActivity extends AppCompatActivity {


    public static final String ID = "ID";
    private ImageView imgDetalhes;
    private TextView tvNome, tvDescricao, tvPreco;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        int id = getIntent().getIntExtra(ID, -1); //ir buscar o conteudo da const ID e dar um default value
        produto = SingletonPalheiro.getInstance(getApplicationContext()).getProduto(id);


        imgDetalhes = findViewById(R.id.ivProdutoDetalhes);
        tvDescricao = findViewById(R.id.tvDescricaoProdutoDetalhes);
        tvNome = findViewById(R.id.tvNomeProdutoDetalhes);
        tvPreco = findViewById(R.id.tvPrecoProdutoDetalhes);

        carregarProduto();

    }


    private void carregarProduto() {
        setTitle("Detalhes:" + produto.getNome());
        tvNome.setText(produto.getNome());
        tvPreco.setText(produto.getPreco() + "");
        tvDescricao.setText(produto.getDescricao());
        //load the image via a url with glyde
        Glide.with(getApplicationContext()).load("127.0.0.1/palheiro/backend/web/index.php?r=image/products&imageName="+produto.getOneImage().getFicheiro()).placeholder(R.drawable.programarandroid2).into(imgDetalhes);

    }


    public void onClickAddCarrinho(View view)
    {

        //create a post request to the API where it
    }
}