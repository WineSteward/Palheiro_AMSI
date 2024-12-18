package com.example.palheiro;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.palheiro.adaptadores.CarrinhoAdaptador;
import com.example.palheiro.listeners.CupaoValidationListener;
import com.example.palheiro.modelo.Carrinho;
import com.example.palheiro.modelo.MetodoExpedicao;
import com.example.palheiro.modelo.MetodoPagamento;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.ArrayList;

public class CarrinhoFragment extends Fragment {

    private Carrinho carrinho;
    private ArrayList<MetodoPagamento> metodosPagamento;
    private ArrayList<MetodoExpedicao> metodosExpedicao;

    private ListView lvCarrinho;
    private Spinner spinnerMetodosPagamento;
    private Spinner spinnerMetodosExpedicao;
    private Button btnComprar;
    private EditText etCupao;

    public CarrinhoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        // Initialize UI components
        lvCarrinho = view.findViewById(R.id.lvCarrinho);
        spinnerMetodosPagamento = view.findViewById(R.id.spinnerMetodosPagamento);
        spinnerMetodosExpedicao = view.findViewById(R.id.spinnerMetodosExpedicao);
        btnComprar = view.findViewById(R.id.btnComprar);
        etCupao = view.findViewById(R.id.etCupao);

        // Fetch data from Singleton
        SingletonPalheiro.getInstance(getContext()).getCarrinhoAPI(getContext());
        carrinho = SingletonPalheiro.getInstance(getContext()).getCarrinho();

        SingletonPalheiro.getInstance(getContext()).getMetodosExpedicaoAPI(getContext());
        metodosExpedicao = SingletonPalheiro.getInstance(getContext()).getMetodosExpedicao();

        SingletonPalheiro.getInstance(getContext()).getMetodosPagamentoAPI(getContext());
        metodosPagamento = SingletonPalheiro.getInstance(getContext()).getMetodosPagamento();

        lvCarrinho.setAdapter(new CarrinhoAdaptador(getContext(), carrinho.getLinhasCarrinho()));

        setupSpinners(getContext());

        // Ao selecionar o botao para concluir a compra
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MetodoPagamento metodoPagamentoSelecionado = metodosPagamento.get(spinnerMetodosPagamento.getSelectedItemPosition());
                MetodoExpedicao metodoExpedicaoSelecionado = metodosExpedicao.get(spinnerMetodosExpedicao.getSelectedItemPosition());

                if (metodoPagamentoSelecionado == null || metodoExpedicaoSelecionado == null) {
                    Toast.makeText(getContext(), "Por favor selecione um método de pagamento/expedição", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etCupao.length() != 0)
                {
                    SingletonPalheiro.getInstance(getContext()).checkCupaoAPI(etCupao.getText().toString(), getContext(), new CupaoValidationListener()
                    {
                        @Override
                        public void onValidationSuccess(boolean isValid)
                        {
                            if (isValid)
                            {
                                Toast.makeText(getContext(), "Cupão Válido", Toast.LENGTH_SHORT).show();

                                //TODO:CUPAO VALID PROCEED HERE

                                // Call API to create a fatura
                                SingletonPalheiro.getInstance(getContext()).postFaturaAPI(getContext(), carrinho, metodoPagamentoSelecionado, metodoExpedicaoSelecionado, etCupao.getText().toString());

                                Toast.makeText(getContext(), "Transaction Complete", Toast.LENGTH_SHORT).show();

                                // Navigate to another fragment
                                Fragment newFragment = new ListaProdutosFragment();
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.contentFragment, newFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Cupão Inválido", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onValidationFailure(String errorMessage) {
                            Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "Please enter a coupon", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void setupSpinners(Context context) {

        ArrayAdapter<MetodoPagamento> MetodoPagamentoAdaptador = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, metodosPagamento);
        MetodoPagamentoAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetodosPagamento.setAdapter(MetodoPagamentoAdaptador);


        ArrayAdapter<MetodoExpedicao> MetodoExpedicaoAdaptador = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, metodosExpedicao);
        MetodoExpedicaoAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetodosExpedicao.setAdapter(MetodoExpedicaoAdaptador);
    }
}
