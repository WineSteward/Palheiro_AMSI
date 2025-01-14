package com.example.palheiro.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.palheiro.R;
import com.example.palheiro.modelo.ListaCompras;

import java.util.ArrayList;

public class ListasComprasAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ListaCompras> listasCompras;

    public ListasComprasAdaptador(Context context, ArrayList<ListaCompras> listasCompras) {
        this.context = context;
        this.listasCompras = listasCompras != null ? listasCompras : new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listasCompras.size();
    }

    @Override
    public Object getItem(int position) {
        return listasCompras.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Assuming ListaCompras has a unique ID, replace `getId()` accordingly
        return listasCompras.get(position).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null) {
            view = inflater.inflate(R.layout.item_lista_compras, null);
        }

        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(view);
            view.setTag(viewHolder);
        }

        viewHolder.update(listasCompras.get(i));
        return view;
    }

    // Update the data set
    public void updateList(ArrayList<ListaCompras> newList) {
        if (listasCompras != null) {
            listasCompras.clear();
            listasCompras.addAll(newList);
            notifyDataSetChanged();
        }
    }

    // ViewHolder class
    private static class ViewHolderLista {
        private TextView tvTituloListaCompras;

        public ViewHolderLista(View view) {
            tvTituloListaCompras = view.findViewById(R.id.tvTituloListaCompras);
        }

        public void update(ListaCompras listaCompras) {
            tvTituloListaCompras.setText(listaCompras.getTitulo());
        }
    }
}
