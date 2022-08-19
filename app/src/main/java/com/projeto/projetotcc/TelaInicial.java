package com.projeto.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TelaInicial extends AppCompatActivity {
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            Fragment fragmentSelecionado;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.receitas:
                        fragmentSelecionado = new Receitas();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentSelecionado).commit();
                        break;
                    case R.id.telaInicial:
                        Intent it = new Intent(getApplicationContext(), TelaInicial.class);
                        startActivity(it);
                        break;
                    case R.id.autenticacao:
                        fragmentSelecionado = new Autenticacao();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentSelecionado).commit();
                        break;
                }

                return true;
            }

        });
    }
}