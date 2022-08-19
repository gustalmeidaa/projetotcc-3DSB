package com.projeto.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Criando os objetos dos componentes da tela
        EditText edEmail = findViewById(R.id.cadEmail);
        EditText edSenha  = findViewById(R.id.cadSenha);
        EditText edNome = findViewById(R.id.cadNome);
        Button cadastrar = findViewById(R.id.btCadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Capturando os valores inseridos pelo usuário
                String email = edEmail.getText().toString().trim();
                String senha = edSenha.getText().toString().trim();
                String nome = edNome.getText().toString().trim();

                if(email.isEmpty() || senha.isEmpty() || nome.isEmpty()){
                    Toast.makeText(view.getContext(), "Preencha todos os campos requisitados", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception ex){
                        Toast.makeText(getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}