package com.example.palheiro.utils;

import com.example.palheiro.modelo.Carrinho;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.LinhaFatura;
import com.example.palheiro.modelo.MetodoExpedicao;
import com.example.palheiro.modelo.MetodoPagamento;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.SingletonPalheiro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FaturaJsonParser
{

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray faturasJson) {
        try {
            ArrayList<Fatura> faturas = new ArrayList<>();

            for (int i = 0; i < faturasJson.length(); i++) {
                JSONObject faturaJson = faturasJson.getJSONObject(i);

                int faturaId = faturaJson.getInt("id");
                double faturaTotal = faturaJson.getDouble("total"); // Example field
                String faturaData = faturaJson.getString("dataVenda");
                JSONObject metodoExpedicaoJson = faturaJson.getJSONObject("metodoexpedicao");
                String faturaExpedicao = metodoExpedicaoJson.getString("nome");

                JSONObject metodoPagamentoJson = faturaJson.getJSONObject("metodopagamento");
                String faturaPagamento = metodoPagamentoJson.getString("nome");

                ArrayList<LinhaFatura> linhasFatura = new ArrayList<>();

                JSONArray linhasFaturaJsonArray = faturaJson.getJSONArray("linhasfatura");

                for (int j = 0; j < linhasFaturaJsonArray.length(); j++) {
                    JSONObject linhaFaturaJson = linhasFaturaJsonArray.getJSONObject(j);

                    int linhaFaturaID = linhaFaturaJson.getInt("id");
                    double linhaFaturaValorUnitario = linhaFaturaJson.getDouble("valorUnitario");
                    int linhaFaturaQuantidade = linhaFaturaJson.getInt("quantidade");
                    double linhaFaturaTotal = linhaFaturaJson.getDouble("total");
                    double linhaFaturaSubtotal = linhaFaturaJson.getDouble("subtotal");
                    double linhaFaturaValorIVA = linhaFaturaJson.getDouble("valorIva");
                    int linhaFaturaIva = linhaFaturaJson.getInt("porcentagemIva");

                    int linhaFaturaProdutoId = linhaFaturaJson.has("produto_id") && !linhaFaturaJson.isNull("produto_id")
                            ? linhaFaturaJson.getInt("produto_id")
                            : 0; // 0 for discount line

                    // Check if it's a discount line (no product ID, or ID is 0/other special value)
                    boolean isDiscountLine = (linhaFaturaProdutoId == 0); // Assuming 0 for discount lines

                    LinhaFatura linhaFatura = new LinhaFatura(
                    linhaFaturaID, linhaFaturaQuantidade, linhaFaturaIva, linhaFaturaTotal,
                    linhaFaturaValorUnitario, linhaFaturaValorIVA, linhaFaturaSubtotal,
                    faturaId, linhaFaturaProdutoId, isDiscountLine
                    );

                    linhasFatura.add(linhaFatura);
                }

                Fatura fatura = new Fatura(faturaId,  faturaData, faturaExpedicao, faturaPagamento, faturaTotal, linhasFatura);

                faturas.add(fatura);
            }

            return faturas;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public static JSONObject parseToJsonFatura(MetodoPagamento metodoPagamento, MetodoExpedicao metodoExpedicao, String cupao)
    {
        // Build the JSON object for the request
        JSONObject faturaJson = new JSONObject();
        try
        {

            faturaJson.put("metodopagamento_id", metodoPagamento.getId());
            faturaJson.put("metodoexpedicao_id", metodoExpedicao.getId());
            faturaJson.put("cupao", cupao);

        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
        return faturaJson;
    }
}
