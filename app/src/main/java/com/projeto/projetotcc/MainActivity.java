package com.projeto.projetotcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

import com.projeto.projetotcc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        trocarFragment(new TelaInicial());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.receitas:
                    trocarFragment(new Receitas());
                    break;
                case R.id.telaInicial:
                    trocarFragment(new TelaInicial());
                    break;
                case R.id.autenticacao:
                    trocarFragment(new Autenticacao());
                    break;
                case R.id.informacoes:
                    trocarFragment(new TelaInformacoes());
                    break;
            }
            return true;
        });
    }

    private void trocarFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }



}