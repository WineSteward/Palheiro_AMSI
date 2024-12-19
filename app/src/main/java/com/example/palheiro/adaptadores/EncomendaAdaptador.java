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

import java.util.ArrayList;

public class EncomendaAdaptador extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fatura> encomendas;

    public EncomendaAdaptador(Context context, ArrayList<Fatura> encomendas) {
        this.context = context;
        this.encomendas = encomendas;
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

    @Override
    public View getView(int i, View view, ViewGroup parent)
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

        EncomendaAdaptador.ViewHolderLista viewHolder = (EncomendaAdaptador.ViewHolderLista) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new EncomendaAdaptador.ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(encomendas.get(i),context); //i é a posicao da encomenda

        return view;
    }

    //classe auxiliar interna
    private class ViewHolderLista
    {
        private TextView tvData, tvEstado;

        public ViewHolderLista(View view)
        {
            tvData = view.findViewById(R.id.tvEncomendaDataItem);
            tvEstado = view.findViewById(R.id.tvEncomendaEstadoItem);
        }

        public void update(Fatura encomenda, Context context)
        {
            tvData.setText(encomenda.getData());

            if(encomenda.getEstadoEncomenda() == 1)
            {
                tvEstado.setText("Entregue");
                tvEstado.setTextColor(ContextCompat.getColor(context, R.color.palheiro_green));
            }
        }
    }

}


