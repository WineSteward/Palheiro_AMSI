package com.example.palheiro.utils;

import com.example.palheiro.modelo.Carrinho;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.MetodoExpedicao;
import com.example.palheiro.modelo.MetodoPagamento;
import com.example.palheiro.modelo.Produto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FaturaJsonParser
{
    public static ArrayList<Fatura> parserJsonFatura(AuthJsonParser faturasJson)
    {
        try
        {

            int id = carrinhoJson.getInt("id");
            float total = carrinhoJson.getInt("total");

            ArrayList<LinhaCarrinho> linhasCarrinho = new ArrayList<>();

            //region - linhasCarrinho json parser
            JSONArray linhasCarrinhoJsonArray = carrinhoJson.getJSONArray("linhasCarrinho");

            // Iterate through the JSONArray
            for (int j = 0; j < linhasCarrinhoJsonArray.length(); j++) {
                try
                {
                    // Get each linhaCarrinho as a JSONObject
                    JSONObject linhaCarrinhoJson = linhasCarrinhoJsonArray.getJSONObject(j);

                    // Extract fields from the JSON object
                    int linhaCarrinhoID = linhaCarrinhoJson.getInt("id");
                    int linhaCarrinhoQuantidade = linhaCarrinhoJson.getInt("quantidade");
                    float linhaCarrinhoPrecoUnitario = linhaCarrinhoJson.getInt("precoUnitario");
                    float linhaCarrinhoTotal = linhaCarrinhoJson.getInt("total");


                    JSONObject linhaCarrinhoProdutoJson = linhaCarrinhoJson.getJSONObject("produto");
                    Produto linhaCarrinhoProduto = ProdutoJsonParser.parserJsonProduto(linhaCarrinhoProdutoJson);

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

    public static JSONObject parseToJsonFatura(Carrinho carrinho, MetodoPagamento metodoPagamento, MetodoExpedicao metodoExpedicao, String cupao)
    {
        // Build the JSON object for the request
        JSONObject faturaJson = new JSONObject();
        try
        {

            faturaJson.put("total", carrinho.getTotal());
            faturaJson.put("metodopagamento_id", metodoPagamento.getId());
            faturaJson.put("metodoexpedicao_id", metodoExpedicao.getId());
            faturaJson.put("descontoNome", cupao);

            JSONArray linhasJsonArray = new JSONArray();

            for (LinhaCarrinho linha : carrinho.getLinhasCarrinho())
            {
                JSONObject linhaJson = new JSONObject();
                linhaJson.put("produto_id", linha.getProduto().getId());
                linhaJson.put("quantidade", linha.getQuantidade());
                linhaJson.put("precoUnitario", linha.getPrecoUnitario());
                linhaJson.put("total", linha.getTotal());
                linhasJsonArray.put(linhaJson);
            }

            faturaJson.put("linhasCarrinho", linhasJsonArray);

        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
        return faturaJson;
    }
}
