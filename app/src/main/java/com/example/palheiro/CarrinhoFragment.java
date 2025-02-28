package com.example.palheiro;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.palheiro.adaptadores.CarrinhoAdaptador;
import com.example.palheiro.listeners.CarrinhoListener;
import com.example.palheiro.modelo.Carrinho;
import com.example.palheiro.modelo.Categoria;
import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.MetodoExpedicao;
import com.example.palheiro.modelo.MetodoPagamento;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class CarrinhoFragment extends Fragment implements CarrinhoListener
{

    private Carrinho carrinho;
    private ArrayList<MetodoPagamento> metodosPagamento;
    private ArrayList<MetodoExpedicao> metodosExpedicao;

    private ListView lvCarrinho;
    private Spinner spinnerMetodosPagamento;
    private Spinner spinnerMetodosExpedicao;
    private Button btnComprar, btnAplicarCupao;
    private EditText etCupao;
    private TextView tvTotalCarrinho;
    private CarrinhoAdaptador carrinhoAdapter;

    public CarrinhoFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        setHasOptionsMenu(true);

        lvCarrinho = view.findViewById(R.id.lvCarrinho);
        btnComprar = view.findViewById(R.id.btnConcluirCompra);
        btnAplicarCupao = view.findViewById(R.id.btnAplicarCupaoItemCarrinho);
        tvTotalCarrinho = view.findViewById(R.id.tvTotalCarrinho);
        etCupao = view.findViewById(R.id.etCupao);
        spinnerMetodosExpedicao = view.findViewById(R.id.spinnerMetodosExpedicao);
        spinnerMetodosPagamento = view.findViewById(R.id.spinnerMetodosPagamento);

        //set de todos os listeners necessarios
        SingletonPalheiro.getInstance(getContext()).setCarrinhoListener(this);

        //fazer pedidos a API
        SingletonPalheiro.getInstance(getContext()).getCarrinhoAPI(getContext());
        SingletonPalheiro.getInstance(getContext()).getMetodosExpedicaoAPI(getContext());
        SingletonPalheiro.getInstance(getContext()).getMetodosPagamentoAPI(getContext());

        //apos pedido feito com sucesso vamos executar o onRefresh method com o listener automaticamente



        btnAplicarCupao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonPalheiro.getInstance(getContext()).checkCupaoAPI(etCupao.getText().toString(), getContext());
            }
        });

        // Ao selecionar o botao para concluir a compra
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MetodoPagamento metodoPagamentoSelecionado = (MetodoPagamento) spinnerMetodosPagamento.getSelectedItem();
                MetodoExpedicao metodoExpedicaoSelecionado = (MetodoExpedicao) spinnerMetodosExpedicao.getSelectedItem();


                if (lvCarrinho.getAdapter() == null || lvCarrinho.getAdapter().getCount() == 0) {
                    Toast.makeText(getContext(), "Carrinho vazio. Adicione itens antes de continuar.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (metodoPagamentoSelecionado == null || metodoPagamentoSelecionado.getId() == -1 ||
                        metodoExpedicaoSelecionado == null || metodoExpedicaoSelecionado.getId() == -1) {
                    Toast.makeText(getContext(), "Por favor selecione um método de pagamento/expedição válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                    // Call API to create a fatura
                    SingletonPalheiro.getInstance(getContext()).postFaturaAPI(getContext(), metodoPagamentoSelecionado, metodoExpedicaoSelecionado, etCupao.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onRefreshCarrinho(Carrinho carrinho)
    {
        if(carrinho != null)
        {
            tvTotalCarrinho.setText(carrinho.getTotal()+"€");

            carrinhoAdapter = new CarrinhoAdaptador(getContext(), carrinho.getLinhasCarrinho());

            lvCarrinho.setAdapter(carrinhoAdapter);
        }
    }

    @Override
    public void onRefreshMetodosPagamento(ArrayList<MetodoPagamento> metodosPagamento, Context context)
    {
        MetodoPagamento defaultMetodoPag = new MetodoPagamento(-1, "Selecione um método pagamento");
        metodosPagamento.add(0, defaultMetodoPag);

        ArrayAdapter<MetodoPagamento> MetodoPagamentoAdaptador = new ArrayAdapter<MetodoPagamento>(context, android.R.layout.simple_spinner_item, metodosPagamento) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Inflate the dropdown view and customize the text color
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if (textView != null) {
                    textView.setTextColor(Color.BLACK);  // Set the dropdown item text color
                }
                view.setBackgroundColor(Color.WHITE);
                return view;
            }
        };

        MetodoPagamentoAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetodosPagamento.setAdapter(MetodoPagamentoAdaptador);
    }

    @Override
    public void onRefreshMetodosExpedicao(ArrayList<MetodoExpedicao> metodosExpedicao, Context context)
    {
        MetodoExpedicao defaultMetodoExp = new MetodoExpedicao(-1, "Selecione um método expedição");
        metodosExpedicao.add(0, defaultMetodoExp);

        ArrayAdapter<MetodoExpedicao> MetodoExpedicaoAdaptador = new ArrayAdapter<MetodoExpedicao>(context, android.R.layout.simple_spinner_item, metodosExpedicao) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // Inflate the dropdown view and customize the text color
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if (textView != null) {
                    textView.setTextColor(Color.BLACK);  // Set the dropdown item text color
                }
                view.setBackgroundColor(Color.WHITE);
                return view;
            }
        };
        MetodoExpedicaoAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetodosExpedicao.setAdapter(MetodoExpedicaoAdaptador);
    }

    @Override
    public void onCupaoResponse(boolean isValid, double value, Context context)
    {
        if(isValid)
        {
            tvTotalCarrinho.setText(value+"€");
            Toast.makeText(context, "Cupão aplicado com sucesso", Toast.LENGTH_SHORT).show();
            return;
        }
            Toast.makeText(context, "Cupão Inválido", Toast.LENGTH_SHORT).show();
            //if not valid display a toast saying the cupon is not valid
    }

    @Override
    public void onCompraConcluida(Context context, boolean cupaoValid) {

        if(!cupaoValid)
        {
            Toast.makeText(context, "Cupao Inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(context, "Compra concluída com sucesso", Toast.LENGTH_SHORT).show();

        //fragment reset
        Fragment newFragment = new CarrinhoFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFragment, newFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onRefreshQuantidade(Context context, int quantidade, double total, LinhaCarrinho linhaCarrinho)
    {
        tvTotalCarrinho.setText(total+"€");
        carrinhoAdapter.updateItemInAdapter(linhaCarrinho);
    }


}
