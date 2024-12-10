package com.example.palheiro;

import android.content.Intent;
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

import java.util.Objects;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    public static final String EMAIL = "Email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Resources res = getResources();
    }

    public void onClickSignIn(View view)
    {
        Resources res = getResources();

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etNIF = findViewById(R.id.etNIF);
        EditText etMorada = findViewById(R.id.etMorada);
        EditText etMorada2 = findViewById(R.id.etMorada2);
        EditText etCodigoPostal = findViewById(R.id.etCodigoPostal);

        String txtEmail = etEmail.getText().toString();
        String txtPassword = etPassword.getText().toString();

        if(!isEmailValid(txtEmail) || !isPasswordValid(txtPassword))
        {
            etEmail.setError("Formato de email inválido");
            etPassword.setError("Use uma password com mais de 3 caracteres");
            return;
        }

        Toast.makeText(this, "Registo realizado com sucecsso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MenuMainActivity.class);
        intent.putExtra(EMAIL, txtEmail);
        startActivity(intent);
        finish(); //impossivel retornar a esta atividade

    }

    private boolean isPasswordValid(String password)
    {
        if(Objects.equals(password, ""))
            return false;

        return password.length() >= 4;
    }

    private boolean isEmailValid(String email)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}