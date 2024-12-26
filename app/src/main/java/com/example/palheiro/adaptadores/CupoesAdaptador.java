package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.palheiro.R;
import com.example.palheiro.modelo.Desconto;
import com.example.palheiro.modelo.ListaCompras;

import java.util.ArrayList;

public class CupoesAdaptador extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Desconto> cupoes;


    public CupoesAdaptador(Context context, ArrayList<Desconto> cupoes)
    {
        this.context = context;
        this.cupoes = cupoes;
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
            view = inflater.inflate(R.layout.item_lista_cupao, null);
        }

        //carregar os dados dos produtos, se já tem os ids das componentes entao nao os volta a inicializar
        CupoesAdaptador.ViewHolderLista viewHolder = (CupoesAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new CupoesAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(cupoes.get(i)); //i é a posicao da lista compras

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private TextView tvCupao;

        //contrutor
        public ViewHolderLista(View view)
        {
            tvCupao = view.findViewById(R.id.tvCupaoItemLista);
        }


        public void update(Desconto cupao)
        {
            tvCupao.setText(cupao.getNome());
        }
    }


}










