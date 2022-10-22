package com.projeto.projetotcc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.projeto.projetotcc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        //Recuperando o token para o envio de mensagens
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Falha ao recuperar o token para notificação", task.getException());
                            return;
                        }

                        // Capturando o token para mensgaem
                        String token = task.getResult();

                        // Logando o token no Logcat
                        Log.e("TOKEN >>>>>>> ", token);
                    }
                });

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