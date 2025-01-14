package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.palheiro.R;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.LinhaFatura;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class DetalhesFaturaAdaptador extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<LinhaFatura> linhasFatura;

    public DetalhesFaturaAdaptador(Context context, ArrayList<LinhaFatura> linhasFatura) {
        this.context = context;
        this.linhasFatura = linhasFatura;
    }

    @Override
    public int getCount() {
        return linhasFatura.size();
    }

    @Override
    public Object getItem(int position) {
        return linhasFatura.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent)
    {

        if(inflater == null)
        {
            //inicializar o inflater
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null)
        {
            view = inflater.inflate(R.layout.item_lista_linha_fatura, null);
        }

        DetalhesFaturaAdaptador.ViewHolderLista viewHolder = (DetalhesFaturaAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new DetalhesFaturaAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(linhasFatura.get(i),context); //i é a posicao da encomenda

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private TextView tvLvFaturaNomeProduto, tvLvFaturaQuantidade, tvLvFaturaPrecoUnitario, tvLvFaturaTotal, tvLvFaturaSubtotal, tvLvFaturaValorIVA, tvLvFaturaIVA;

        public ViewHolderLista(View view)
        {
            tvLvFaturaNomeProduto = view.findViewById(R.id.tvLvFaturaNomeProduto);
            tvLvFaturaQuantidade = view.findViewById(R.id.tvLvFaturaQuantidade);
            tvLvFaturaPrecoUnitario = view.findViewById(R.id.tvLvFaturaPrecoUnitario);
            tvLvFaturaTotal = view.findViewById(R.id.tvLvFaturaTotalLinha);
            tvLvFaturaSubtotal = view.findViewById(R.id.tvLvFaturaSubtotalLinha);
            tvLvFaturaValorIVA = view.findViewById(R.id.tvLvFaturaTotalIVA);
            tvLvFaturaIVA = view.findViewById(R.id.tvLvFaturaPercentagemIVA);
        }

        public void update(LinhaFatura linhaFatura, Context context)
        {
            if(linhaFatura.isDiscountLine())
            {
                tvLvFaturaNomeProduto.setText(R.string.txtLvFaturaLinhaDescontoTitulo);
            }
            else {
                Produto produto = SingletonPalheiro.getInstance(context).getProduto(linhaFatura.getProdutoId());
                tvLvFaturaNomeProduto.setText(produto.getNome());
            }
            tvLvFaturaQuantidade.setText(linhaFatura.getQuantidade()+"");
            tvLvFaturaPrecoUnitario.setText(linhaFatura.getValorUnitario()+"€");
            tvLvFaturaTotal.setText(linhaFatura.getTotal()+"€");
            tvLvFaturaSubtotal.setText(linhaFatura.getSubtotal()+"€");
            tvLvFaturaValorIVA.setText(linhaFatura.getValorIva()+"€");
            tvLvFaturaIVA.setText(linhaFatura.getPercentagemIva()+"");
        }
    }

}


