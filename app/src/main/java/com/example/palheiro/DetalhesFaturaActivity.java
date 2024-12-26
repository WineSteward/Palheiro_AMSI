package com.example.palheiro;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.palheiro.adaptadores.DetalhesFaturaAdaptador;
import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.SingletonPalheiro;

public class DetalhesFaturaActivity extends AppCompatActivity {

    public static final String ID = "ID";
    private TextView tvNumeroFatura, tvMetodoPagamento, tvMetodoExpedicao, tvTotal, tvData;
    private ListView lvLinhasFatura;
    private Fatura fatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_fatura);

        int id = getIntent().getIntExtra(ID, -1); //ir buscar o conteudo da const ID e dar um default value
        fatura = SingletonPalheiro.getInstance(getApplicationContext()).getFatura(id);

        tvNumeroFatura = findViewById(R.id.tvNumeroFaturaDetalhes);
        tvMetodoPagamento = findViewById(R.id.tvMetodoPagamentoFaturaDetalhes);
        tvMetodoExpedicao = findViewById(R.id.tvMetodoExpedicaoFaturaDetalhes);
        tvTotal = findViewById(R.id.tvTotalCompraFaturaDetalhes);
        tvData = findViewById(R.id.tvDataFaturaDetalhes);
        lvLinhasFatura = findViewById(R.id.lvLinhasFatura);

        carregarFatura();
    }

    private void carregarFatura()
    {
        setTitle("Fatura");
        tvNumeroFatura.setText(fatura.getId());
        tvTotal.setText(fatura.getTotal() + "€");
        tvMetodoExpedicao.setText(fatura.getMetodoExpedicaoNome());
        tvMetodoPagamento.setText(fatura.getMetodoPagamentoNome());
        tvData.setText(fatura.getData());
        lvLinhasFatura.setAdapter(new DetalhesFaturaAdaptador(getApplicationContext(), fatura.getLinhasFatura()));
    }
}