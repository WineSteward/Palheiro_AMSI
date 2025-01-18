package com.example.palheiro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.palheiro.listeners.ServerListener;
import com.example.palheiro.modelo.SingletonPalheiro;

public class ServidorActivity extends AppCompatActivity implements ServerListener {

    private EditText etServerIP;
    private Button btnConnectToServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor);

        SingletonPalheiro.getInstance(getApplicationContext()).setServerListener(this);

        etServerIP = findViewById(R.id.etServerIP);
        btnConnectToServer = findViewById(R.id.btnSubmitServerIP);

    }

    public void onClickConnectToServer(View view)
    {
        // Update the server IP in the Singleton
        SingletonPalheiro.getInstance(getApplicationContext()).setIP(etServerIP.getText().toString(), getApplicationContext());
    }

    @Override
    public void onUpdateServerIP(Context context)
    {
        Toast.makeText(context, "Server IP estabelecido com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }
}