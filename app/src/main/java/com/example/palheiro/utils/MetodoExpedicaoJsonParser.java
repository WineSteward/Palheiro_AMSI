package com.example.palheiro.utils;

import com.example.palheiro.modelo.ListaCompras;
import com.example.palheiro.modelo.MetodoExpedicao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MetodoExpedicaoJsonParser
{
    public static ArrayList<MetodoExpedicao> parserJsonMetodoExpedicao(JSONArray response)
    {
        ArrayList<MetodoExpedicao> metodosExpedicao = new ArrayList<>();

        for( int i = 0; i < response.length(); i++)
        {
            JSONObject metodosExpedicaoJson = null;

            try
            {
                metodosExpedicaoJson = (JSONObject) response.get(i);

                int id = metodosExpedicaoJson.getInt("id");
                String nome = metodosExpedicaoJson.getString("nome");

                metodosExpedicao.add(new MetodoExpedicao(id, nome));

            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        }

        return metodosExpedicao;
    }

}
