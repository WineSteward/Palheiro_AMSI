package com.example.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.books.R;
import com.example.books.modelo.Livro;

import java.util.ArrayList;

public class GrelhaLivrosAdaptador extends BaseAdapter
{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public GrelhaLivrosAdaptador(Context context, ArrayList<Livro> livros) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //carregar o layout e depois todos os livros que fazem parte da lista

        if(inflater == null)
        {
            //inicializar o inflater
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null)
        {
            //carregar o layout com o inflater
            view = inflater.inflate(R.layout.item_grelha_livro, null);
        }

        //carregar os dados dos livros, se já tem os ids das componentes entao nao os volta a inicializar
        ViewHolderGrelha viewHolder = (ViewHolderGrelha) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolderGrelha(view);
            view.setTag(viewHolder);
        }

        //update do conteudo das componentes pois ja estao inicializadas
        viewHolder.update(livros.get(i)); //i é a posicao do livro

        return view;
    }



    //classe auxiliar interna
    private class ViewHolderGrelha
    {
        private ImageView imgCapaItemGrelha;

        //contrutor
        public ViewHolderGrelha(View view)
        {
            imgCapaItemGrelha = view.findViewById(R.id.imgCapaItemGrelha);
        }


        public void update(Livro livro)
        {
            imgCapaItemGrelha.setImageResource(livro.getCapa());
        }
    }

}
