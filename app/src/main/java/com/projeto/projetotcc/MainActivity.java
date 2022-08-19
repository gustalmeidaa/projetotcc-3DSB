package com.projeto.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    String email, senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criando os objetos do componente da tela
        EditText edEmail = findViewById(R.id.edEmail);
        EditText edSenha = findViewById(R.id.edSenha);
        TextView cad = findViewById(R.id.txtCadastro);
        Button btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //Capturando os valores inseridos pelo usuário
                    email = edEmail.getText().toString().trim();
                    senha = edSenha.getText().toString().trim();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent it = new Intent(MainActivity.this, TelaInicial.class);
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "DEU RUIM, KLÉBER DA MONTANHA", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
                }


            }
        });

        cad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try{
                    Intent it = new Intent(MainActivity.this, Cadastro.class);
                    startActivity(it);


                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Erro inesperado", Toast.LENGTH_LONG).show();

                }
            }
        });
    }




}