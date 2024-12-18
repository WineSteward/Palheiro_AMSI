package com.example.palheiro.utils;

import com.example.palheiro.modelo.MetodoPagamento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MetodoPagamentoJsonParser
{
    public static ArrayList<MetodoPagamento> parserJsonMetodoPagamento(JSONArray response)
    {
        ArrayList<MetodoPagamento> metodosPagamento = new ArrayList<>();

        for( int i = 0; i < response.length(); i++)
        {
            JSONObject metodosPagamentoJson = null;

            try
            {
                metodosPagamentoJson = (JSONObject) response.get(i);

                int id = metodosPagamentoJson.getInt("id");
                String nome = metodosPagamentoJson.getString("nome");

                metodosPagamento.add(new MetodoPagamento(id, nome));

            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        }

        return metodosPagamento;
    }
}
