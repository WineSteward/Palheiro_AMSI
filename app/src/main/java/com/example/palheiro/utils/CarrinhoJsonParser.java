package com.example.palheiro.utils;

import com.example.palheiro.modelo.Carrinho;
import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.Produto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarrinhoJsonParser
{
    public static Carrinho parserJsonCarrinho(JSONObject carrinhoJson)
    {
        try
        {

            int id = carrinhoJson.getInt("id");
            float total = carrinhoJson.getInt("total");

            ArrayList<LinhaCarrinho> linhasCarrinho = new ArrayList<>();

            //region - linhasCarrinho json parser
            JSONArray linhasCarrinhoJsonArray = carrinhoJson.getJSONArray("linhascarrinho");

            // Iterate through the JSONArray
            for (int j = 0; j < linhasCarrinhoJsonArray.length(); j++) {
                try
                {
                    // Get each linhaCarrinho as a JSONObject
                    JSONObject linhaCarrinhoJson = linhasCarrinhoJsonArray.getJSONObject(j);

                    // Extract fields from the JSON object
                    int linhaCarrinhoID = linhaCarrinhoJson.getInt("id");
                    int linhaCarrinhoQuantidade = linhaCarrinhoJson.getInt("quantidade");
                    float linhaCarrinhoPrecoUnitario = (float) linhaCarrinhoJson.getDouble("precoUnitario");
                    float linhaCarrinhoTotal = (float) linhaCarrinhoJson.getDouble("total");


                    JSONObject linhaCarrinhoProdutoJson = linhaCarrinhoJson.getJSONObject("produto");
                    Produto linhaCarrinhoProduto = ProdutoJsonParser.parserJsonProdutoSimple(linhaCarrinhoProdutoJson);

                    // Create a linhaCarrinho
                    LinhaCarrinho linhaCarrinho = new LinhaCarrinho(linhaCarrinhoID, linhaCarrinhoPrecoUnitario, linhaCarrinhoTotal, linhaCarrinhoQuantidade, linhaCarrinhoProduto);

                    // Add the Image object to the array
                    linhasCarrinho.add(linhaCarrinho);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }

            //endregion

            return new Carrinho(id, total, linhasCarrinho);

        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }
}
