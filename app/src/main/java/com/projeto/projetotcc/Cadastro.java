package com.projeto.projetotcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Cadastro extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Criando os objetos dos componentes da tela
        EditText edEmail = findViewById(R.id.cadEmail);
        EditText edSenha  = findViewById(R.id.cadSenha);
        EditText edNome = findViewById(R.id.cadNome);
        Button cadastrar = findViewById(R.id.btCadastrar);
        EditText confirmaSenha = findViewById(R.id.confirmaSenha);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Capturando os valores inseridos pelo usuário
                String email = edEmail.getText().toString().trim();
                String senha = edSenha.getText().toString().trim();
                String nome = edNome.getText().toString().trim();
                String confirmarSenha= confirmaSenha.getText().toString().trim();

                if(senha.length() < 6){
                    Toast.makeText(Cadastro.this, "A senha deve conter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                }

                //Verificando se os campos de email, senha e nome estão devidamente preenchidos
                if(email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() || nome.isEmpty()) {
                    Toast.makeText(view.getContext(), "Preencha todos os campos requisitados", Toast.LENGTH_SHORT).show();
                }

                if(!confirmarSenha.equals(senha)){
                    Toast.makeText(view.getContext(), "Verifique se você digitou sua senha corretamente.", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                if(task.isSuccessful()){
                                    Usuario usuario = new Usuario(nome, email);
                                    String id = FirebaseAuth.getInstance().getUid();
                                    //Caso todas as ações estiverem corretas, será criado um novo objeto
                                    //da classe "Usuário", que será atribuído ao UID no documento
                                    //'usuarios' no banco de dados
                                    db.collection("usuarios").document(id).set(usuario);
                                    Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
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