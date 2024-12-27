package com.example.palheiro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener
{

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.ndOpen, R.string.ndClose);

        toggle.syncState();

        drawer.addDrawerListener(toggle);

        carregarCabecalho();

        navigationView.setNavigationItemSelectedListener(this); //Listener, quando clica executa funcao

        fragmentManager = getSupportFragmentManager();

        carregarFragmentoInicial();
    }

    private boolean carregarFragmentoInicial()
    {
        Menu menu = navigationView.getMenu(); //vamos buscar o menu na barra lateral

        MenuItem item = menu.getItem(0); //vamos buscar o 1 item no menu array style

        item.setChecked(true);

        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho()
    {
        username = getIntent().getStringExtra(LoginActivity.USERNAME); //ir buscar o conteudo da const EMAIL da activity login

        SharedPreferences sharedPreferences = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);

        if(username != null)
        {
           SharedPreferences.Editor editor = sharedPreferences.edit();

           editor.putString("USERNAME", username);
           editor.apply();
        }
        else
        {
            username = sharedPreferences.getString("USERNAME", "Sem username");
        }

        View hView = navigationView.getHeaderView(0);

        TextView nav_tvUsername = hView.findViewById(R.id.tvUsername);

        nav_tvUsername.setText(username);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Fragment fragment = null;

        if(item.getItemId()== R.id.navProdutos)
            {
            fragment = new ProdutosFragment();

            setTitle(item.getTitle());
        }
        else if(item.getItemId() == R.id.navCarrinho)
        {

            fragment = new CarrinhoFragment();

            setTitle(item.getTitle());

        }
        else if(item.getItemId()== R.id.navEncomendas)
        {
            fragment = new EncomendasFragment();

            setTitle(item.getTitle());

        }
        else if(item.getItemId()== R.id.navFaturas)
        {
            fragment = new FaturasFragment();

            setTitle(item.getTitle());

        }
        else if(item.getItemId()== R.id.navListasCompras)
        {
            fragment = new ListasComprasFragment();

            setTitle(item.getTitle());

        }
        else if(item.getItemId()== R.id.navPerfil)
        {
            fragment = new CupoesFragment();

            setTitle(item.getTitle());

        }
        else if(item.getItemId()== R.id.navPerfil)
        {
            fragment = new PerfilFragment();

            setTitle(item.getTitle());

        }

        drawer.closeDrawer(GravityCompat.START);

        if(fragment != null)
        {
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit(); //substituir algo pedaco de layout pelo fragmento
        }
        return true;
    }
}





