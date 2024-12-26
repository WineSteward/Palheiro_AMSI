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
import com.example.palheiro.modelo.ListaCompras;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class ListasComprasAdaptador extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListaCompras> listasCompras;


    public ListasComprasAdaptador(Context context, ArrayList<ListaCompras> listasCompras)
    {
        this.context = context;
        this.listasCompras = listasCompras;
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
            view = inflater.inflate(R.layout.item_lista_compras, null);
        }

        //carregar os dados dos produtos, se já tem os ids das componentes entao nao os volta a inicializar
        ListasComprasAdaptador.ViewHolderLista viewHolder = (ListasComprasAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ListasComprasAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(listasCompras.get(i)); //i é a posicao da lista compras

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private TextView tvTituloListaCompras;

        //contrutor
        public ViewHolderLista(View view)
        {
            tvTituloListaCompras = view.findViewById(R.id.tvTituloListaCompras);
        }


        public void update(ListaCompras listaCompras)
        {
            tvTituloListaCompras.setText(listaCompras.getTitulo());
        }
    }


}










