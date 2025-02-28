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
import com.example.palheiro.modelo.UserProfile;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity implements AuthListener
{

    private String txtUsername;
    public static final String USERNAME = "Username";
    private EditText etUsername, etPassword, etEmail, etNIF, etMorada, etMorada2, etCodigoPostal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etNIF = findViewById(R.id.etNIF);
        etMorada = findViewById(R.id.etMorada);
        etMorada2 = findViewById(R.id.etMorada2);
        etCodigoPostal = findViewById(R.id.etCodigoPostal);

        SingletonPalheiro.getInstance(getApplicationContext()).setAuthListener(this);

    }

    public void onClickSignIn(View view)
    {

        String txtEmail = etEmail.getText().toString();
        txtUsername = etUsername.getText().toString();
        String txtPassword = etPassword.getText().toString();
        String txtNIF = etNIF.getText().toString();
        String txtMorada = etMorada.getText().toString();
        String txtMorada2 = etMorada2.getText().toString();
        String txtCP = etCodigoPostal.getText().toString();

        if(!isPasswordValid(txtPassword))
        {
            etPassword.setError("Formato inválido, indique pelo menos 3 caracteres");
            return;
        }
        if(!isEmailValid(txtEmail))
        {
            etEmail.setError("Email inválido");
            return;
        }

        UserProfile profile = new UserProfile(txtPassword, txtNIF, txtMorada, txtMorada2, txtCP, txtUsername, txtEmail);
        // Call the Singleton method to start the sign-in process via the API
        SingletonPalheiro.getInstance(getApplicationContext()).signinUserAPI(getApplicationContext(), profile);
    }

    private boolean isPasswordValid(String password)
    {
        // Check if the password is empty or shorter than 3 characters
        if(Objects.equals(password, ""))
            return false;

        return password.length() >= 3;
    }

    // Helper method to validate email format using Android's email pattern utility
    private boolean isEmailValid(String email)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @Override
    public void onUpdateSignin(Context context, String response)
    {
        switch (response) {
            case "username":
                etUsername.setError("Username já está em uso.");
                return;
            case "email":
                etEmail.setError("Email já está em uso.");
                return;
            case "NIF":
                    etNIF.setError("NIF já está em uso.");
                return;
            case "error":
                Toast.makeText(context, "Erro, tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                return;
            default:
                SharedPreferences sharedPref = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", response);
                editor.apply(); //save assincrono

                Toast.makeText(this, "Registo realizado com sucecsso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MenuMainActivity.class);
                intent.putExtra(USERNAME, txtUsername);
                startActivity(intent);
                finish(); //impossivel retornar a esta atividade
        }
    }


    @Override
    public void onUpdateLogin(String token) {

    }

}