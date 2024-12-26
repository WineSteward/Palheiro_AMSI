package com.example.palheiro;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.palheiro.listeners.ProdutosListener;
import com.example.palheiro.modelo.Categoria;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class DetalhesProdutoActivity extends AppCompatActivity implements ProdutosListener {


    public static final String ID = "ID";
    private ImageView imgDetalhes;
    private TextView tvNome, tvDescricao, tvPreco, tvValorNutricional;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        SingletonPalheiro.getInstance(getApplicationContext()).setProdutosListener(this);

        int id = getIntent().getIntExtra(ID, -1); //ir buscar o conteudo da const ID e dar um default value
        produto = SingletonPalheiro.getInstance(getApplicationContext()).getProduto(id);

        imgDetalhes = findViewById(R.id.ivProdutoDetalhes);
        tvDescricao = findViewById(R.id.tvDescricaoProdutoDetalhes);
        tvNome = findViewById(R.id.tvNomeProdutoDetalhes);
        tvPreco = findViewById(R.id.tvPrecoProdutoDetalhes);
        tvValorNutricional = findViewById(R.id.tvValorNutricionalDetalhes);

        carregarProduto();

    }


    private void carregarProduto()
    {
        setTitle("Detalhes:" + produto.getNome());
        tvNome.setText(produto.getNome());
        tvPreco.setText(produto.getPreco() + "€");
        tvDescricao.setText(produto.getDescricao());
        tvValorNutricional.setText(produto.getValornutricional().getNome());
        //load the image via a url with glyde
        Glide.with(getApplicationContext()).load(SingletonPalheiro.mURLAPIProdutoImage + produto.getOneImage().getFicheiro()).placeholder(R.drawable.programarandroid2).into(imgDetalhes);
    }


    public void onClickAddCarrinho(View view)
    {
        SingletonPalheiro.getInstance(getApplicationContext()).addLinhaCarrinhoAPI(produto.getId(), getApplicationContext());
    }

    @Override
    public void onProdutoAddCarrinho(Context context)
    {
        Toast.makeText(context, "Produto adicionado ao carrinho com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshProdutos(ArrayList<Produto> produtos) {

    }

    @Override
    public void onRefreshCategorias(ArrayList<Categoria> categorias, Context context) {

    }

}