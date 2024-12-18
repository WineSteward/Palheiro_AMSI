package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palheiro.R;
import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class CarrinhoAdaptador extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<LinhaCarrinho> linhasCarrinho;


    public CarrinhoAdaptador(Context context, ArrayList<LinhaCarrinho> linhasCarrinho)
    {
        this.context = context;
        this.linhasCarrinho = linhasCarrinho;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override //por cada linha do carrinho este metodo é executado
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
            view = inflater.inflate(R.layout.item_carrinho, null);
        }

        //carregar os dados da linha do carrinho, se já tem os ids das componentes entao nao os volta a inicializar
        CarrinhoAdaptador.ViewHolderLista viewHolder = (CarrinhoAdaptador.ViewHolderLista) view.getTag();

        if(viewHolder == null)
        {
            viewHolder = new CarrinhoAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(linhasCarrinho.get(i)); //i é a posicao da lista compras

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private ImageView ivImagemItemCarrinho;
        private TextView tvNomeItemCarrinho, tvQuantidadeItemCarrinho, tvMarcaItemCarrinho, tvTotalItemCarrinho;

        //contrutor
        public ViewHolderLista(View view)
        {
            ivImagemItemCarrinho = view.findViewById(R.id.ivImagemItemCarrinho);
            tvNomeItemCarrinho = view.findViewById(R.id.tvNomeItemCarrinho);
            tvQuantidadeItemCarrinho = view.findViewById(R.id.tvQuantidadeItemCarrinho);
            tvMarcaItemCarrinho = view.findViewById(R.id.tvMarcaItemCarrinho);
            tvTotalItemCarrinho = view.findViewById(R.id.tvTotalItemCarrinho);
        }


        public void update(LinhaCarrinho linhaCarrinho)
        {
            Glide.with(context).load(SingletonPalheiro.mURLAPIProdutoImage + linhaCarrinho.getProduto().getOneImage().getFicheiro()).placeholder(R.drawable.programarandroid2).into(ivImagemItemCarrinho);
            tvNomeItemCarrinho.setText(linhaCarrinho.getProduto().getNome());
            tvQuantidadeItemCarrinho.setText(linhaCarrinho.getQuantidade());
            tvMarcaItemCarrinho.setText(linhaCarrinho.getProduto().getMarca().getNome());
            tvTotalItemCarrinho.setText(linhaCarrinho.getTotal()+"€");
        }
    }


}


