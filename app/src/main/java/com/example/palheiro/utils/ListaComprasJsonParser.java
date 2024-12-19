package com.example.palheiro.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.palheiro.modelo.ListaCompras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaComprasJsonParser
{
    public static ArrayList<ListaCompras> parserJsonListasCompras(JSONArray response)
    {
        ArrayList<ListaCompras> listasCompras = new ArrayList<>();

        for( int i = 0; i < response.length(); i++)
        {
            JSONObject listaComprasJson = null;

            try
            {
                listaComprasJson = (JSONObject) response.get(i);

                int id = listaComprasJson.getInt("id");
                String titulo = listaComprasJson.getString("titulo");
                String descricao = listaComprasJson.getString("descricao");

                listasCompras.add(new ListaCompras(id, titulo, descricao));

            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        }

        return listasCompras;
    }

    public static ListaCompras parserJsonListaCompras(JSONObject listaComprasJson)
    {
        ListaCompras listaCompras = null;

        try
        {
            int id = listaComprasJson.getInt("id");
            String titulo = listaComprasJson.getString("titulo");
            String descricao = listaComprasJson.getString("descricao");

            listaCompras = new ListaCompras(id, titulo, descricao);

        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

        return listaCompras;
    }

    public static boolean isConnectionInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}
