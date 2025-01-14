package com.example.palheiro.modelo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
import com.example.palheiro.listeners.CupaoListener;
import com.example.palheiro.listeners.EncomendasListener;
import com.example.palheiro.listeners.FaturasListener;
import com.example.palheiro.listeners.ListaComprasListener;
import com.example.palheiro.listeners.ListasComprasListener;
import com.example.palheiro.listeners.AuthListener;
import com.example.palheiro.listeners.ProdutosListener;
import com.example.palheiro.listeners.ProfileListener;
import com.example.palheiro.listeners.ServerListener;
import com.example.palheiro.utils.AuthJsonParser;
import com.example.palheiro.utils.CarrinhoJsonParser;
import com.example.palheiro.utils.CategoriaJsonParser;
import com.example.palheiro.utils.CupaoJsonParser;
import com.example.palheiro.utils.EncomendaJsonParser;
import com.example.palheiro.utils.FaturaJsonParser;
import com.example.palheiro.utils.ListaComprasJsonParser;
import com.example.palheiro.utils.MetodoExpedicaoJsonParser;
import com.example.palheiro.utils.MetodoPagamentoJsonParser;
import com.example.palheiro.utils.ProdutoJsonParser;
import com.example.palheiro.utils.ProfileJsonParser;

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

    private ArrayList<Fatura> encomendas;
    private ArrayList<MetodoExpedicao> metodosExpedicao;
    private ArrayList<MetodoPagamento> metodosPagamento;
    private ArrayList<Fatura> faturas;
    private ArrayList<Produto> produtos;
    private ArrayList<Categoria> categorias;
    private ArrayList<Desconto> cupoes;
    private Produto produto;
    private Carrinho carrinho;
    private Fatura fatura;
    private ListaCompras listaCompras;
    private UserProfile profile;

    private static String IP = "http://192.168.1.71/palheiroAPI/backend/web/";
    // "172.22.21.209"; //IP do servidor

    //region - API endpoits
    public static final String mURLAPIProdutos = IP + "api/produto/";
    public static final String mURLAPIProdutoImage = IP + "products/";
    public static final String mURLAPILogin = IP + "api/auth/login";
    public static final String mURLAPILogout = IP + "api/auth/logout";
    public static final String mURLAPIListaCompras = IP + "api/listacompra/";
    public static final String mURLAPICarrinho = IP + "api/carrinho/";
    public static final String mURLAPICategoria = IP + "api/categoria/";
    public static final String mURLAPIFaturas = IP + "api/fatura/";
    public static final String mURLAPICupoes = IP + "api/cupao/";
    public static final String mURLAPIMetodosPagamento = IP + "api/metodopagamento/";
    public static final String mURLAPIMetodosExpedicao = IP + "api/metodoexpedicao/";
    public static final String mURLAPIEncomendas = IP + "api/encomenda/";
    public static final String mURLAPIProfile = IP + "api/userprofile/";

    //endregion
    private static RequestQueue volleyQueue = null;
    private static String TOKEN = "Receive token from successful api call to login";

    private EncomendasListener encomendasListener;
    private CarrinhoListener carrinhoListener;
    private ProdutosListener produtosListener;
    private ListaComprasListener listaComprasListener;
    private ListasComprasListener listasComprasListener;
    private AuthListener authListener;
    private FaturasListener faturasListener;
    private ServerListener serverListener;
    private ProfileListener profileListener;
    private CupaoListener cupaoListener;

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
        listasCompras = new ArrayList<>();
        listasComprasBD = new ListaComprasBDHelper(context);

        categorias = new ArrayList<>();
        produtos = new ArrayList<>();
        faturas = new ArrayList<>();
        produtos = new ArrayList<>();
        cupoes = new ArrayList<>();
    }

    //region - set Listeners

    public void setCupaoListener(CupaoListener cupaoListener)
    {
        this.cupaoListener = cupaoListener;
    }

    public void setProfileListener(ProfileListener profileListener)
    {
        this.profileListener = profileListener;
    }

    public void setServerListener(ServerListener serverListener)
    {
        this.serverListener = serverListener;
    }

    public void setFaturasListener(FaturasListener faturasListener)
    {
        this.faturasListener = faturasListener;
    }

    public void setEncomendasListener(EncomendasListener encomendasListener)
    {
        this.encomendasListener = encomendasListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener)
    {
        this.carrinhoListener = carrinhoListener;
    }

    public void setProdutosListener(ProdutosListener produtosListener)
    {
        this.produtosListener = produtosListener;
    }

    public void setListaComprasListener(ListaComprasListener listaComprasListener)
    {
        this.listaComprasListener = listaComprasListener;
    }

    public void setListasComprasListener(ListasComprasListener listasComprasListener)
    {
        this.listasComprasListener = listasComprasListener;
    }

    public void setAuthListener(AuthListener authListener)
    {
        this.authListener = authListener;
    }

    //endregion

    public void setIP(String newIP, Context context)
    {
        IP = newIP;
        if(serverListener != null)
            serverListener.onUpdateServerIP(context);
    }

    public void setTOKEN(String newToken, Context context)
    {
        TOKEN = newToken;
    }

    public String getTOKEN()
    {
        return TOKEN;
    }

    //region - auth API

    public void loginAPI(Context context, String username, String password)
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
                    params.put("username", username);
                    params.put("password", password);
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
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIProdutos+"all?acess-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API

                    produtos = ProdutoJsonParser.parserJsonProdutos(response);

                    //atualizar a vista
                    if (produtosListener != null)
                    {
                        produtosListener.onRefreshProdutos(produtos);
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

    //endregion

    //region - Categoria Acess

    public void getAllCategoriasAPI(final Context context)
    {
        if(!isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPICategoria+"all?acess-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API

                    categorias = CategoriaJsonParser.parserJsonCategorias(response);

                    //atualizar o spinner
                    if (produtosListener != null)
                    {
                        produtosListener.onRefreshCategorias(categorias, context);
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

        public void getAllListasComprasAPI(final Context context)
        {
            if (!isConnectionInternet(context)) {
                //nao tem ligacao a internet
                Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

                //offline only
                listasCompras = getListasComprasBD();

                //atualizar a vista
                if (listasComprasListener != null) {
                    listasComprasListener.onRefreshListasCompras(listasCompras);
                }
            } else {
                JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIListaCompras + "all?access-token=" + TOKEN, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //handle success call to API

                        listasCompras = ListaComprasJsonParser.parserJsonListasCompras(response);

                        //offline only
                        addListasComprasBD(listasCompras);

                        //atualizar a vista
                        if (listaComprasListener != null) {
                            listasComprasListener.onRefreshListasCompras(listasCompras);
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

        public void addListaComprasAPI(final ListaCompras listaCompras, final Context context)
        {
            if(! isConnectionInternet(context))
            {
                //nao tem ligacao a internet
                Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

            }
            else
            {
                JSONObject listaCompraJson = new JSONObject();
                try
                {
                    listaCompraJson.put("titulo", listaCompras.getTitulo());
                    listaCompraJson.put("descricao", listaCompras.getDescricao());

                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

                JsonObjectRequest reqInsert = new JsonObjectRequest(Request.Method.POST, mURLAPIListaCompras+"add?access-token="+TOKEN, listaCompraJson, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        //handle success call to API

                        ListaCompras listaCompras = ListaComprasJsonParser.parserJsonListaCompras(response); //parse de JSON para Modelo
                        addListaComprasBD(listaCompras); //adicionar a BD das listas de compras

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
                });
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
            else
            {
                JSONObject listaCompraJson = new JSONObject();
                try
                {
                    listaCompraJson.put("titulo", listaCompras.getTitulo());
                    listaCompraJson.put("descricao", listaCompras.getDescricao());

                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

                StringRequest reqUpdate = new StringRequest(Request.Method.PUT, mURLAPIListaCompras+"id/"+listaCompras.getId()+"?access-token="+TOKEN, new Response.Listener<String>() {
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
                });
                volleyQueue.add(reqUpdate);
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
                StringRequest reqDelete = new StringRequest(Request.Method.DELETE, mURLAPIListaCompras+"/"+listaCompras.getId()+"?access-token="+TOKEN, new Response.Listener<String>() {
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
                });
                volleyQueue.add(reqDelete);
            }
        }

        //endregion

    //endregion

    //region - Carrinho/Linha Carrinho

    public void getCarrinhoAPI(final Context context)
    {
        if(!isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

        }
        else
        {
            JsonObjectRequest reqSelect = new JsonObjectRequest(Request.Method.GET, mURLAPICarrinho+"my?access-token="+TOKEN, null, new Response.Listener<JSONObject>() {
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
                    params.put("access-token", TOKEN);
                    return params;
                }
            };
            volleyQueue.add(reqSelect);
        }
    }

    public void addLinhaCarrinhoAPI(final int produto_id, final Context context)
    {
        if(! isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StringRequest reqInsert = new StringRequest(Request.Method.POST, mURLAPICarrinho+"id/"+produto_id+"?access-token="+TOKEN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {

                    //handle success call to API
                    if(produtosListener != null)
                        produtosListener.onProdutoAddCarrinho(context);

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
                    params.put("access-token", TOKEN);
                    return params;
                }
            };
            volleyQueue.add(reqInsert);
        }
    }

    public void editLinhaCarrinhoAPI(final LinhaCarrinho linhaCarrinho, int quantidade, final Context context)
    {
        if(! isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {

            JSONObject quantidadeJson = new JSONObject();
            try
            {
                quantidadeJson.put("quantidade", quantidade);
            } catch (JSONException e)
            {
                throw new RuntimeException(e);
            }

            JsonObjectRequest reqUpdate = new JsonObjectRequest(Request.Method.PUT, mURLAPICarrinho+"id/"+linhaCarrinho.getId()+"?access-token="+TOKEN, quantidadeJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {
                    //handle success call to API
                    double total;
                    try
                    {
                        total = response.getDouble("total");
                    } catch (JSONException e)
                    {
                        throw new RuntimeException(e);
                    }

                    linhaCarrinho.setQuantidade(quantidade);

                    //atualizar a vista
                    if(carrinhoListener != null)
                        carrinhoListener.onRefreshQuantidade(context, quantidade, total, linhaCarrinho);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null) {
                        Log.e("EditLinhaCarrinhoAPI", "Status Code: " + error.networkResponse.statusCode);
                        Log.e("EditLinhaCarrinhoAPI", "Error: " + new String(error.networkResponse.data));
                    } else {
                        Log.e("EditLinhaCarrinhoAPI", "Request Failed: " + error.getMessage());
                    }
                }
            });
            volleyQueue.add(reqUpdate);
        }
    }

    public Carrinho getCarrinho()
    {
        if(carrinho != null)
            return carrinho;

        return null;
    }

    //endregion

    //region - Encomenda

    public void getAllEncomendasAPI(final Context context)
    {
        if(! isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

        }
        else
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIEncomendas+"all?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    //handle success call to API

                    encomendas = EncomendaJsonParser.parserJsonEncomendas(response);

                    //atualizar a vista
                    if(encomendasListener != null)
                    {
                        encomendasListener.onRefreshEncomendas(encomendas);
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

    //endregion

    //region - Fatura

    public Fatura getFatura(int id)
    {
        for (Fatura fatura: faturas)
        {
            if(fatura.getId() == id)
                return fatura;
        }

        return null;
    }

    public void postFaturaAPI(final Context context, MetodoPagamento metodoPagamento, MetodoExpedicao metodoExpedicao, String cupao)
    {
        if(isConnectionInternet(context))
        {
            JSONObject faturaJson = FaturaJsonParser.parseToJsonFatura(metodoPagamento, metodoExpedicao, cupao);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, mURLAPIFaturas+"new?acess-token="+TOKEN, faturaJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the API

                            Toast.makeText(context, "Transação Concluída", Toast.LENGTH_SHORT).show();
                            String res;
                            boolean cupaoValid = false;
                            try {
                                res = response.getString("response");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            if(!res.equals("Invalid"))
                                cupaoValid = true;

                            if(carrinhoListener != null)
                            {
                                carrinhoListener.onCompraConcluida(context, cupaoValid);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            volleyQueue.add(postRequest);
        }
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllFaturasAPI(final Context context)
    {
        if(! isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();

        }
        else
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIFaturas+"all?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    //handle success call to API

                    faturas = FaturaJsonParser.parserJsonFaturas(response);

                    if(faturasListener != null)
                    {
                        faturasListener.onRefreshFaturas(faturas);
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

    //endregion

    //region Userprofile

    public void getProfileAPI(final Context context)
    {
        if(!isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            JsonObjectRequest reqSelect = new JsonObjectRequest(Request.Method.GET, mURLAPIProfile + "my?access-token="+TOKEN, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //handle api successful call

                    profile = ProfileJsonParser.parserJsonProfile(response);

                    //atualizar a vista
                    if (profileListener != null) {
                        profileListener.onRefreshProfile(profile);
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

    public void editProfileAPI(final Context context, String morada, String morada2, String cp)
    {
        if(! isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else {

            JSONObject profileJson = new JSONObject();
            try
            {
                profileJson.put("morada", morada);
                profileJson.put("morada2", morada2);
                profileJson.put("codigoPostal", cp);

            } catch (JSONException e)
            {
                throw new RuntimeException(e);
            }

            JsonObjectRequest reqUpdate = new JsonObjectRequest(Request.Method.PUT, mURLAPIProfile+"edit?access-token="+TOKEN, profileJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //handle success call to API

                    //atualizar a vista
                    if(profileListener != null)
                        profileListener.onUpdateProfile(context);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //something went wrong
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(reqUpdate);
        }
    }

    public void signinUserAPI(final Context context, final UserProfile profile)
    {
        if(! isConnectionInternet(context))
        {
            //nao tem ligacao a internet
            Toast.makeText(context, "Sem Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            JSONObject profileJson = new JSONObject();
            try
            {
                profileJson.put("username", profile.getUsername());
                profileJson.put("email", profile.getEmail());
                profileJson.put("NIF", profile.getNif());
                profileJson.put("password", profile.getPassword());
                profileJson.put("morada2", profile.getMorada2());
                profileJson.put("morada", profile.getMorada());
                profileJson.put("codigoPostal", profile.getCodigoPostal());

            } catch (JSONException e)
            {
                throw new RuntimeException(e);
            }


            JsonObjectRequest reqInsert = new JsonObjectRequest(Request.Method.POST, mURLAPIProfile+"/new",profileJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //handle success call to API

                    String res;
                    try {
                        res = response.getString("response");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    if(authListener != null)
                        authListener.onUpdateSignin(context, res);

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
                    params.put("access-token", TOKEN);
                    return params;
                }
            };
            volleyQueue.add(reqInsert);
        }
    }

    //endregion

    //region - Metodos Pagamento
    public void getMetodosPagamentoAPI(final Context context)
    {
        if(isConnectionInternet(context))
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIMetodosPagamento+"all?access-token="+TOKEN, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API
                    metodosPagamento = MetodoPagamentoJsonParser.parserJsonMetodoPagamento(response);

                    //atualizar a vista
                    if(carrinhoListener != null)
                    {
                        carrinhoListener.onRefreshMetodosPagamento(metodosPagamento, context);
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
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPIMetodosExpedicao+"all?access-token="+TOKEN, null, new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API
                    metodosExpedicao = MetodoExpedicaoJsonParser.parserJsonMetodoExpedicao(response);

                    //atualizar a vista
                    if(carrinhoListener != null)
                    {
                        carrinhoListener.onRefreshMetodosExpedicao(metodosExpedicao, context);
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


    //region - Cupao

    public void checkCupaoAPI(String cupao, final Context context)
    {
        if (isConnectionInternet(context))
        {
            JsonObjectRequest reqSelect = new JsonObjectRequest(Request.Method.GET, mURLAPICupoes+"validate?access-token="+TOKEN+"&descontoNome="+cupao,null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            try
                            {
                                boolean isValid = response.getBoolean("isValid");

                                if(isValid) {
                                    double novoTotal = response.getDouble("novoTotal");

                                    if (carrinhoListener != null) {
                                        carrinhoListener.onCupaoResponse(isValid, novoTotal, context);
                                    }
                                }
                                else {
                                    if (carrinhoListener != null) {
                                        carrinhoListener.onCupaoResponse(isValid, 0, context);
                                    }
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

                        }
                    });

            volleyQueue.add(reqSelect);
        }
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllCupoesAPI(final Context context)
    {
        if(isConnectionInternet(context))
        {
            JsonArrayRequest reqSelect = new JsonArrayRequest(Request.Method.GET, mURLAPICupoes+"all?access-token="+TOKEN, null, new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response) {

                    //handle success call to API
                    cupoes = CupaoJsonParser.parserJsonCupoes(response);

                    //atualizar a vista
                    if(cupaoListener != null)
                    {
                        cupaoListener.onRefreshCupoes(context, cupoes);
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
        else
        {
            Toast.makeText(context, "Sem Ligação à Internet", Toast.LENGTH_SHORT).show();
        }
    }

    //endregion

    public static boolean isConnectionInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnectedOrConnecting();
    }
}














































