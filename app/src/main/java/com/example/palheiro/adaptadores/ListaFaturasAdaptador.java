package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palheiro.R;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class ListaFaturasAdaptador extends BaseAdapter
{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fatura> faturas;


    public ListaFaturasAdaptador(Context context, ArrayList<Fatura> faturas) {
        this.context = context;
        this.faturas = faturas;
    }

    @Override
    public int getCount() {
        return faturas.size();
    }

    @Override
    public Object getItem(int position) {
        return faturas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return faturas.get(i).getId();
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
            view = inflater.inflate(R.layout.item_lista_fatura, null);
        }

        //carregar os dados dos produtos, se já tem os ids das componentes entao nao os volta a inicializar
        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(faturas.get(i)); //i é a posicao do produto

        return view;
    }


    //classe auxiliar interna
    private class ViewHolderLista
    {
        private TextView tvData, tvNumeroFatura;

        //contrutor
        public ViewHolderLista(View view)
        {
            tvNumeroFatura = view.findViewById(R.id.tvNumeroFaturaLista);
            tvData = view.findViewById(R.id.tvDataFaturaLista);
        }


        public void update(Fatura fatura)
        {
            tvNumeroFatura.setText(fatura.getId()+"");
            tvData.setText(fatura.getData());
        }
    }


}










