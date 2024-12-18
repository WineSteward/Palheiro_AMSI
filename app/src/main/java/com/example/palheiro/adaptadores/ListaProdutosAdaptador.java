package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palheiro.R;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class ListaProdutosAdaptador extends BaseAdapter
{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Produto> produtos;


    public ListaProdutosAdaptador(Context context, ArrayList<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int i) {
        return produtos.get(i).getId();
    }

    @Override //por cada produto este metodo é executado
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        //carregar o layout e depois todos os produtos que fazem parte da lista

        if(inflater == null)
        {
            //inicializar o inflater
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null)
        {
            //carregar o layout com o inflater
            view = inflater.inflate(R.layout.item_lista_produto, null);
        }

        //carregar os dados dos produtos, se já tem os ids das componentes entao nao os volta a inicializar
        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(produtos.get(i)); //i é a posicao do produto

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private ImageView imgProduto;
        private TextView tvNomeProduto, tvPrecoProduto, tvMarcaProduto;

        //contrutor
        public ViewHolderLista(View view)
        {
            tvNomeProduto = view.findViewById(R.id.tvNomeProduto);
            tvPrecoProduto = view.findViewById(R.id.tvPrecoProduto);
            tvMarcaProduto = view.findViewById(R.id.tvMarcaProduto);
            imgProduto = view.findViewById(R.id.ivProdutoLista);
        }


        public void update(Produto produto)
        {
            tvNomeProduto.setText(produto.getNome());
            tvPrecoProduto.setText(produto.getPreco() + "");
            tvMarcaProduto.setText(produto.getMarca().getNome());
            //load the image via a url with glyde
            Glide.with(context).load(SingletonPalheiro.mURLAPIProdutoImage + produto.getOneImage().getFicheiro()).placeholder(R.drawable.programarandroid2).into(imgProduto);

        }
    }


}










