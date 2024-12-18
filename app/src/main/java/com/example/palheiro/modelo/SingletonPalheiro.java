package com.example.palheiro.modelo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.palheiro.ListasComprasFragment;
import com.example.palheiro.listeners.CarrinhoListener;
import com.example.palheiro.listeners.CupaoValidationListener;
import com.example.palheiro.listeners.ListaComprasListener;
import com.example.palheiro.listeners.ListasComprasListener;
import com.example.palheiro.listeners.AuthListener;
import com.example.palheiro.listeners.ProdutoListener;
import com.example.palheiro.listeners.ProdutosListener;
import com.example.palheiro.utils.AuthJsonParser;
import com.example.palheiro.utils.CarrinhoJsonParser;
import com.example.palheiro.utils.FaturaJsonParser;
import com.example.palheiro.utils.ListaComprasJsonParser;
import com.example.palheiro.utils.MetodoExpedicaoJsonParser;
import com.example.palheiro.utils.MetodoPagamentoJsonParser;
import com.example.palheiro.utils.ProdutoJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SingletonPalheiro
{
    private ArrayList<ListaCompras> listasCompras;
    private ListaComprasBDHelper listasComprasBD = null;


    private static SingletonPalheiro instance = null;

    private ArrayList<MetodoExpedicao> metodosExpedicao;
    private ArrayList<MetodoPagamento> metodosPagamento;
    private ArrayList<Produto> produtos;
    private Produto produto;
    private Carrinho carrinho;

    private static final String IP = "172.22.21.209"; //IP do servidor
    //region - API endpoits
    public static final String mURLAPIProdutos = IP + "/palheiro/backend/web/api/produtos";
    public static final String mURLAPIProdutoImage = IP + "/palheiro/backend/web/image/products&imageName=";
    public static final String mURLAPILogin = IP + "/palheiro/backend/web/api/login";
    public static final String mURLAPIListaCompras = IP + "/palheiro/backend/web/api/listaCompras";
    public static final String mURLAPICarrinho = IP + "/palheiro/backend/web/api/carrinho";
    public static final String mURLAPIFaturas = IP + "/palheiro/backend/web/api/fatura";
    public static final String mURLAPICupoes = IP + "/palheiro/backend/web/api/cupoes";
    public static final String mURLAPIMetodosPagamento = IP + "/palheiro/backend/web/api/metodosPagamento";
    public static final String mURLAPIMetodosExpedicao = IP + "/palheiro/backend/web/api/metodosExpedicao";

    //endregion
    private static RequestQueue volleyQueue = null;
    private static final String TOKEN = "Receive token from successful api call to login";


    private CarrinhoListener carrinhoListener;
    private ProdutosListener produtosListener;
    private ProdutoListener produtoListener;
    private ListaComprasListener listaComprasListener;
    private ListasComprasListener listasComprasListener;

    private AuthListener authListener;

    public static synchronized SingletonPalheiro getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new SingletonPalheiro(context);
            volleyQueue = Volley.newRequestQueue(context);
        }

        return instance;
    }

    private SingletonPalheiro(Context context)
    {
        listasCompras = new ArrayList<ListaCompras>();
        listasComprasBD = new ListaComprasBDHelper(context);

        produtos = new ArrayList<Produto>();

    }

    //region - set Listeners

    public void setCarrinhoListener(CarrinhoListener carrinhoListener)
    {
        this.carrinhoListener = carrinhoListener;
    }

    public void setProdutosListener(ProdutosListener produtosListener)
    {
        this.produtosListener = produtosListener;
    }

    public void setProdutoListener(ProdutoListener produtoListener)
    {
        this.produtoListener = produtoListener;
    }

    public void setListaComprasListener(ListaComprasListener listaComprasListener)
    {
        this.listaComprasListener = listaComprasListener;
    }

    public void setListasComprasListener(ListasComprasListener listasComprasListener)
    {
        this.listasComprasListener = listasComprasListener;
    }

    public void setLoginListener(AuthListener authListener)
    {
        this.authListener = authListener;
    }

    public void loginAPI(Context context, String email, String password)
    {
        if(! ListaComprasJsonParser.isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            StringRequest reqInsert = new StringRequest(Request.Method.POST, mURLAPILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //handle success call to API

                    String token = AuthJsonParser.parserJsonLogin(response);

                    //atualizar a vista
                    if(authListener != null)
                        authListener.onUpdateLogin(token);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //something went wrong
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(reqInsert);
        }
    }

    public void singupAPI(Context context, String email, String password, String username, String morada, String morada2, String NIF, String codigoPostal)
    {
        if(! ListaComprasJsonParser.isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else {
            StringRequest reqInsert = new StringRequest(Request.Method.POST, mURLAPILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //handle success call to API

                    String token = AuthJsonParser.parserJsonSignIn(response);

                    //atualizar a vista
                    if(authListener != null)
                        authListener.onUpdateLogin(token);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //something went wrong
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("username", username);
                    params.put("NIF", NIF);
                    params.put("morada", morada);
                    params.put("morada2", morada2);
                    params.put("codigoPostal", codigoPostal);
                    return params;
                }
            };
            volleyQueue.add(reqInsert);
        }
    }

    //endregion

    //region - Produto Access

    public void getAllProdutosAPI(final Context context)
    {
        if(!isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API

                    produtos = ProdutoJsonParser.parserJsonProdutos(response);

                    //atualizar a vista
                    if (produtosListener != null) {
                        produtosListener.onRefreshDetalhes(produtos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(reqSelect);
        }
    }

    public void getOneProdutoAPI(final Context context, int id)
    {
        if(!isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            JsonObjectRequest reqSelect = new JsonObjectRequest(Request.Method.GET, mURLAPIProdutos + "/id/" + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //handle api successful call

                    produto = ProdutoJsonParser.parserJsonProduto(response);

                    //atualizar a vista
                    if (produtoListener != null) {
                        produtoListener.onRefreshDetalhes(produto);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            volleyQueue.add(reqSelect);
        }
    }


    public Produto getProduto(int id)
    {
        for (Produto produto: produtos)
        {
            if(produto.getId() == id)
                return produto;
        }

        return null;
    }

    public ArrayList<Produto> getProdutos()
    {
        return new ArrayList<>(produtos);
    }

    public void addProduto(Produto produto)
    {
        produtos.add(produto);
    }

    //endregion

    //region - ListaCompras Access

        public ListaCompras getListaCompras(int id)
        {
            for(ListaCompras listaCompras : listasCompras)
            {
                if(listaCompras.getId() == id)
                    return listaCompras;
            }
            return null;
        }

        //region - BD
        public ArrayList<ListaCompras> getListasComprasBD()
        {
            listasCompras = listasComprasBD.getAllListasComprasBD();

            return new ArrayList<>(listasCompras);
        }

        public void addListasComprasBD(ArrayList<ListaCompras> listasCompras)
        {
            listasComprasBD.deleteAllListasComprasBD();

            for(ListaCompras listaCompras : listasCompras)
            {
                addListaComprasBD(listaCompras);
            }
        }

        public void addListaComprasBD(ListaCompras listaCompras)
        {
            listasComprasBD.addListaComprasBD(listaCompras);
        }

        public void editListaComprasBD(ListaCompras listaCompras)
        {
            ListaCompras updatedListaCompras = getListaCompras(listaCompras.getId());

            if(updatedListaCompras != null)
            {
                listasComprasBD.updateListaComprasBD(updatedListaCompras);
            }
        }

        public void deleteListaComprasBD(int id)
        {
            ListaCompras listaCompras = getListaCompras(id);

            if(listaCompras != null)
            {
                listasCompras.remove(listaCompras);
            }
        }
        //endregion

        //region - API
        public void addListaComprasAPI(final ListaCompras listaCompras, final Context context)
        {
            if(! isConnectionInternet(context))
            {
                //nao tem ligacao a internet
                Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            }
            else
            {
                StringRequest reqInsert = new StringRequest(Request.Method.POST, mURLAPIListaCompras, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //handle success call to API

                        ListaCompras listaCompras = ListaComprasJsonParser.parserJsonListaCompras(response); //parse de JSON para Modelo
                        addListaComprasBD(listaCompras); //adicionar ao Array das listas de compras

                        //atualizar a vista
                        if(listaComprasListener != null)
                            listaComprasListener.onRefreshListasCompras(ListasComprasFragment.ADD);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //something went wrong
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("titulo", listaCompras.getTitulo());
                        params.put("descricao", listaCompras.getDescricao());
                        params.put("token", TOKEN);
                        return params;
                    }
                };
                volleyQueue.add(reqInsert);
            }
        }

        public void editListaComprasAPI(final ListaCompras listaCompras, final Context context)
        {
            if(! isConnectionInternet(context))
            {
                //nao tem ligacao a internet
                Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            }
            else {
                StringRequest reqUpdate = new StringRequest(Request.Method.PUT, mURLAPIListaCompras+"/"+listaCompras.getId(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //handle success call to API
                        editListaComprasBD(listaCompras);

                        //atualizar a vista
                        if(listaComprasListener != null)
                            listaComprasListener.onRefreshListasCompras(ListasComprasFragment.EDIT);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //something went wrong
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("titulo", listaCompras.getTitulo());
                        params.put("descricao", listaCompras.getDescricao());
                        params.put("token", TOKEN);
                        return params;
                    }
                };
                volleyQueue.add(reqUpdate);
            }
        }

        public void getAllListasComprasAPI(final Context context)
        {
            if(! isConnectionInternet(context))
            {
                //nao tem ligacao a internet
                Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

                //offline only
                listasCompras = getListasComprasBD();

                //atualizar a vista
                if(listasComprasListener != null)
                {
                    listasComprasListener.onRefreshListasCompras(listasCompras);
                }
            }
            else
            {
                JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIListaCompras, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //handle success call to API

                        listasCompras = ListaComprasJsonParser.parserJsonListasCompras(response);

                        //offline only
                        addListasComprasBD(listasCompras);

                        //atualizar a vista
                        if(listaComprasListener != null)
                        {
                            listasComprasListener.onRefreshListasCompras(listasCompras);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", TOKEN);
                        return params;
                    }
                };
                volleyQueue.add(reqSelect);
            }
        }

        public void deleteListaComprasAPI(final ListaCompras listaCompras, final Context context)
        {
            if(! isConnectionInternet(context))
            {
                //nao tem ligacao a internet
                Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
            }
            else
            {
                StringRequest reqDelete = new StringRequest(Request.Method.DELETE, mURLAPIListaCompras+"/"+listaCompras.getId(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //handle success call to API

                        deleteListaComprasBD(listaCompras.getId());

                        //atualizar a vista
                        if(listaComprasListener != null)
                            listaComprasListener.onRefreshListasCompras(ListasComprasFragment.DELETE);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", TOKEN);
                        return params;
                    }
                };
                volleyQueue.add(reqDelete);
            }
        }

        //endregion

    //endregion

    //region  - Carrinho/Fatura Access
    public void getCarrinhoAPI(final Context context)
    {
        if(!isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

        }
        else
        {
            JsonObjectRequest reqSelect = new JsonObjectRequest(Request.Method.GET, mURLAPICarrinho, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    //handle success call to API

                    carrinho = CarrinhoJsonParser.parserJsonCarrinho(response);

                    //atualizar a vista
                    if(carrinhoListener != null)
                    {
                        carrinhoListener.onRefreshCarrinho(carrinho);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    return params;
                }
            };
            volleyQueue.add(reqSelect);
        }
    }

    public void postFaturaAPI(final Context context, Carrinho carrinho, MetodoPagamento metodoPagamento, MetodoExpedicao metodoExpedicao, String cupao)
    {
        if(isConnectionInternet(context))
        {
            JSONObject faturaJson = FaturaJsonParser.parseToJsonFatura(carrinho, metodoPagamento, metodoExpedicao, cupao);


            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, mURLAPIFaturas, faturaJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the API

                            //on the API side I need to clear the carrinho
                            Toast.makeText(context, "Transação Concluída", Toast.LENGTH_SHORT).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle any error from the request
                            Toast.makeText(context, "Transação falhou: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    return params;
                }
            };

            volleyQueue.add(postRequest);
        }
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public Carrinho getCarrinho()
    {
        if(carrinho != null)
            return carrinho;

        return null;
    }

    //endregion

    //region - Metodos Pagamento
    public void getMetodosPagamentoAPI(final Context context)
    {
        if(isConnectionInternet(context))
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIMetodosPagamento, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API
                    metodosPagamento = MetodoPagamentoJsonParser.parserJsonMetodoPagamento(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(reqSelect);
        }
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<MetodoPagamento> getMetodosPagamento()
    {
        return new ArrayList<>(metodosPagamento);
    }
    //endregion

    //region Metodos Expedicao
    public  void getMetodosExpedicaoAPI(final Context context)
    {
        if(isConnectionInternet(context))
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIMetodosExpedicao, null, new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API
                    metodosExpedicao = MetodoExpedicaoJsonParser.parserJsonMetodoExpedicao(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(reqSelect);
        }
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<MetodoExpedicao> getMetodosExpedicao()
    {
        return new ArrayList<>(metodosExpedicao);
    }
    //endregion









    public void checkCupaoAPI(String cupao, final Context context, final CupaoValidationListener listener)
    {
        if (isConnectionInternet(context))
        {

            StringRequest reqSelect = new StringRequest(Request.Method.GET, mURLAPICupoes,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // Parse the JSON response
                                JSONObject responseJson = new JSONObject(response);
                                String res = responseJson.getString("valid");

                                // Notify listener of result
                                if (Objects.equals(res, "valid"))
                                {
                                    listener.onValidationSuccess(true);
                                }
                                else
                                {
                                    listener.onValidationSuccess(false);
                                }
                            }
                            catch (JSONException e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onValidationFailure("API Error: " + error.getMessage());
                        }
                    })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("descontoNome", cupao);
                    return params;
                }
            };

            volleyQueue.add(reqSelect);
        }
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean isConnectionInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnectedOrConnecting();
    }
}














































