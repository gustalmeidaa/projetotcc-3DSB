package com.projeto.projetotcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criando os objetos do componente da tela
        EditText uEmail = findViewById(R.id.edEmail);
        EditText senha = findViewById(R.id.edSenha);
        Button btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uEmail.equals("gusta@gusta") && senha.equals("123")){
                    Toast.makeText(getApplicationContext(), "Usuário autenticado com sucesso!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), "Esta página ainda está em construção!", Toast.LENGTH_LONG).show();
            }
        });
    }




}