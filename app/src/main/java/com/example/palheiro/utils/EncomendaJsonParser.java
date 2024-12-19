package com.example.palheiro.utils;

import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.ListaCompras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EncomendaJsonParser
{
    public static ArrayList<Fatura> parserJsonEncomendas(JSONArray response)
    {
        ArrayList<Fatura> encomendas = new ArrayList<>();

        for( int i = 0; i < response.length(); i++)
        {
            JSONObject encomendasComprasJson = null;

            try
            {
                encomendasComprasJson = (JSONObject) response.get(i);

                int id = encomendasComprasJson.getInt("id");
                int estadoEncomenda = encomendasComprasJson.getInt("estadoEncomenda");
                String data = encomendasComprasJson.getString("data");

                encomendas.add(new Fatura(id, estadoEncomenda, data));

            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        }

        return encomendas;
    }
}
