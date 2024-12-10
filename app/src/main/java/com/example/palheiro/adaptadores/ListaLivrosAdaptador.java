package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.palheiro.R;
import com.example.palheiro.modelo.Livro;

import java.util.ArrayList;

public class ListaLivrosAdaptador extends BaseAdapter
{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public ListaLivrosAdaptador(Context context, ArrayList<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int i) {
        return livros.get(i).getId();
    }

    @Override //por cada livro este metodo é executado
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        //carregar o layout e depois todos os livros que fazem parte da lista

        if(inflater == null)
        {
            //inicializar o inflater
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null)
        {
            //carregar o layout com o inflater
            view = inflater.inflate(R.layout.item_lista_livro, null);
        }

        //carregar os dados dos livros, se já tem os ids das componentes entao nao os volta a inicializar
        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(livros.get(i)); //i é a posicao do livro

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private ImageView imgCapa;
        private TextView tvTitulo, tvSeries, tvAutor, tvAno;

        //contrutor
        public ViewHolderLista(View view)
        {
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvSeries = view.findViewById(R.id.tvSerie);
            tvAutor = view.findViewById(R.id.tvAutor);
            tvAno = view.findViewById(R.id.tvAno);
            imgCapa = view.findViewById(R.id.imgCapa);
        }


        public void update(Livro livro)
        {
            tvTitulo.setText(livro.getTitulo());
            tvSeries.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(livro.getAno() + "");
            imgCapa.setImageResource(livro.getCapa());
        }
    }


}










