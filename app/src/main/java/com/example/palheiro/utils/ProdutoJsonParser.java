package com.example.palheiro.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.palheiro.modelo.Categoria;
import com.example.palheiro.modelo.Imagem;
import com.example.palheiro.modelo.Iva;
import com.example.palheiro.modelo.Marca;
import com.example.palheiro.modelo.Produto;
import com.example.palheiro.modelo.ValorNutricional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProdutoJsonParser
{

    public static ArrayList<Produto> parserJsonProdutos(JSONArray response)
    {
        ArrayList<Produto> produtos = new ArrayList<>();

        for (int i = 0; i < response.length(); i++)
        {
            JSONObject produtoJson = null;

            try
            {
                produtoJson = (JSONObject) response.get(i);

                int id = produtoJson.getInt("id");
                String nome = produtoJson.getString("nome");
                String descricao = produtoJson.getString("descricao");
                float preco = (float) produtoJson.getDouble("preco");
                int quantidade = produtoJson.getInt("quantidade");

                //nested objects inside the received product
                JSONObject categoriaJson = produtoJson.getJSONObject("categoria");
                int categoriaId = categoriaJson.getInt("id");
                String categoriaNome = categoriaJson.getString("nome");

                JSONObject marcaJson = produtoJson.getJSONObject("marca");
                int marcaId = marcaJson.getInt("id");
                String marcaNome = marcaJson.getString("nome");

                JSONObject ivaJson = produtoJson.getJSONObject("iva");
                int ivaId = ivaJson.getInt("id");
                int ivaValorPorcentagem = ivaJson.getInt("valorPorcentagem");

                JSONObject valorNutricionalJson = produtoJson.getJSONObject("valornutricional");
                int valorNutricionalId = valorNutricionalJson.getInt("id");
                String valorNutricionalNome = valorNutricionalJson.getString("nome");

                // Create the objects that compromise a product
                ArrayList<Imagem> imagens = new ArrayList<>();

                //region - imagens json parser
                JSONArray imagensJsonArray = produtoJson.getJSONArray("imagens");

                // Iterate through the JSONArray
                for (int j = 0; j < imagensJsonArray.length(); j++) {
                    try
                    {
                        // Get each image as a JSONObject
                        JSONObject imagemJson = imagensJsonArray.getJSONObject(j);

                        // Extract fields from the image JSON object
                        int imagemId = imagemJson.getInt("id");
                        String imagemFicheiro = imagemJson.getString("ficheiro");

                        // Create an Image object
                        Imagem imagem = new Imagem(id, imagemFicheiro);

                        // Add the Image object to the array
                        imagens.add(imagem);
                    }
                    catch (JSONException e)
                    {
                        throw new RuntimeException(e);
                    }
                }

                Categoria categoria = new Categoria(categoriaId, categoriaNome);

                Iva iva = new Iva(ivaId, ivaValorPorcentagem);

                Marca marca = new Marca(marcaId, marcaNome);

                ValorNutricional valorNutricional = new ValorNutricional(valorNutricionalId, valorNutricionalNome);

                produtos.add(new Produto(id, categoria, iva, marca, valorNutricional, quantidade, preco, nome, descricao, imagens));

            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }
        }
        return produtos;
    }

    public static Produto parserJsonProduto(JSONObject produtoJson)
    {
        try
        {

            int id = produtoJson.getInt("id");
            String nome = produtoJson.getString("nome");
            String descricao = produtoJson.getString("descricao");
            float preco = (float) produtoJson.getDouble("preco");
            int quantidade = produtoJson.getInt("quantidade");

            //nested objects inside the received product
            JSONObject categoriaJson = produtoJson.getJSONObject("categoria");
            int categoriaId = categoriaJson.getInt("id");
            String categoriaNome = categoriaJson.getString("nome");

            JSONObject marcaJson = produtoJson.getJSONObject("marca");
            int marcaId = marcaJson.getInt("id");
            String marcaNome = marcaJson.getString("nome");

            JSONObject ivaJson = produtoJson.getJSONObject("iva");
            int ivaId = marcaJson.getInt("id");
            int ivaValorPorcentagem = marcaJson.getInt("valorPorcentagem");

            JSONObject valorNutricionalJson = produtoJson.getJSONObject("iva");
            int valorNutricionalId = valorNutricionalJson.getInt("id");
            String valorNutricionalNome = valorNutricionalJson.getString("nome");

            // Create the objects that compromise a product
            ArrayList<Imagem> imagens = new ArrayList<>();

            //region - imagens json parser
            JSONArray imagensJsonArray = produtoJson.getJSONArray("imagens");

            // Iterate through the JSONArray
            for (int j = 0; j < imagensJsonArray.length(); j++) {
                try
                {
                    // Get each image as a JSONObject
                    JSONObject imagemJson = imagensJsonArray.getJSONObject(j);

                    // Extract fields from the image JSON object
                    int imagemId = imagemJson.getInt("id");
                    String imagemFicheiro = imagemJson.getString("ficheiro");

                    // Create an Image object
                    Imagem imagem = new Imagem(id, imagemFicheiro);

                    // Add the Image object to the array
                    imagens.add(imagem);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }

            Categoria categoria = new Categoria(categoriaId, categoriaNome);

            Iva iva = new Iva(ivaId, ivaValorPorcentagem);

            Marca marca = new Marca(marcaId, marcaNome);

            ValorNutricional valorNutricional = new ValorNutricional(valorNutricionalId, valorNutricionalNome);

            return new Produto(id, categoria, iva, marca, valorNutricional, quantidade, preco, nome, descricao, imagens);

        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Produto parserJsonProdutoSimple(JSONObject produtoJson)
    {
        try
        {

            int id = produtoJson.getInt("id");
            String nome = produtoJson.getString("nome");
            float preco = (float) produtoJson.getDouble("preco");
            int quantidade = produtoJson.getInt("quantidade");

            JSONObject marcaJson = produtoJson.getJSONObject("marca");
            int marcaId = marcaJson.getInt("id");
            String marcaNome = marcaJson.getString("nome");

            Marca marca = new Marca(marcaId, marcaNome);

            // Create the objects that compromise a product
            ArrayList<Imagem> imagens = new ArrayList<>();

            //region - imagens json parser
            JSONArray imagensJsonArray = produtoJson.getJSONArray("imagens");

            // Iterate through the JSONArray
            for (int j = 0; j < imagensJsonArray.length(); j++) {
                try
                {
                    // Get each image as a JSONObject
                    JSONObject imagemJson = imagensJsonArray.getJSONObject(j);

                    // Extract fields from the image JSON object
                    int imagemId = imagemJson.getInt("id");
                    String imagemFicheiro = imagemJson.getString("ficheiro");

                    // Create an Image object
                    Imagem imagem = new Imagem(id, imagemFicheiro);

                    // Add the Image object to the array
                    imagens.add(imagem);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }

            return new Produto(id, nome, preco, quantidade, imagens, marca);

        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
    }


}


























