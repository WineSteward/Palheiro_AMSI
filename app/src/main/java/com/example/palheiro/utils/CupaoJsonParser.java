package com.example.palheiro.utils;

import com.example.palheiro.modelo.Desconto;
import com.example.palheiro.modelo.Fatura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CupaoJsonParser
{
    public static ArrayList<Desconto> parserJsonCupoes(JSONArray response)
    {
        ArrayList<Desconto> cupoes = new ArrayList<>();

        for( int i = 0; i < response.length(); i++)
        {
            JSONObject cupaoJson = null;

            try
            {
                cupaoJson = (JSONObject) response.get(i);

                int id = cupaoJson.getInt("id");
                String nome = cupaoJson.getString("nome");

                cupoes.add(new Desconto(id, nome));

            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        }

        return cupoes;
    }
}
