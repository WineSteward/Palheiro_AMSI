package com.example.palheiro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.palheiro.listeners.AuthListener;
import com.example.palheiro.modelo.SingletonPalheiro;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements AuthListener {

    public static final String USERNAME = "Username";
    private String txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Resources res = getResources();

    }

    public void onClickLogin(View view)
    {
        Resources res = getResources();

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        txtUsername = etUsername.getText().toString();
        String txtPassword = etPassword.getText().toString();

        if(!isPasswordValid(txtPassword))
        {
            etPassword.setError("Credenciais Incorretas");
            return;
        }

        SingletonPalheiro.getInstance(getApplicationContext()).loginAPI(getApplicationContext(), txtUsername, txtPassword);
    }

    public void onClickRedirectSignIn(View view)
    {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private boolean isPasswordValid(String password)
    {
        if(Objects.equals(password, ""))
            return false;

        return password.length() >= 4;
    }

    @Override
    public void onUpdateLogin(String token)
    {
        if(token.isEmpty())
        {
            Toast.makeText(this, "Credenciais Incorretas", Toast.LENGTH_SHORT).show();
            return;
        }

        //guardar o token no shared pref
        SharedPreferences sharedPref = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply(); //save assincrono
        //editor.commit(); // save sincrono

        Toast.makeText(this, "Login realizado com sucecsso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MenuMainActivity.class);
        intent.putExtra(USERNAME, txtUsername);

        startActivity(intent);
        finish(); //impossivel retornar a esta atividade
    }
}



