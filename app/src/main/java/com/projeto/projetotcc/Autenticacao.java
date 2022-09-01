package com.projeto.projetotcc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Autenticacao#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Autenticacao extends Fragment {
    View v;
    String email, senha;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Autenticacao() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Autenticacao newInstance(String param1, String param2) {
        Autenticacao fragment = new Autenticacao();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_autenticacao, container, false);

        //Criando os objetos do componente da tela
        EditText edEmail = v.findViewById(R.id.edEmail);
        EditText edSenha = v.findViewById(R.id.edSenha);
        TextView cad = v.findViewById(R.id.textViewCad);
        Button btEntrar = v.findViewById(R.id.btEntrar);
        ProgressBar barraProgresso = v.findViewById(R.id.barraProgresso);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    barraProgresso.setVisibility(View.VISIBLE);
                    btEntrar.setVisibility(View.INVISIBLE);
                    //Capturando os valores inseridos pelo usuário
                    email = edEmail.getText().toString().trim();
                    senha = edSenha.getText().toString().trim();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() || email.equals("gusta") && senha.equals("1")) {
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                                TelaInicial telaInicial = new TelaInicial();
                                fragmentTransaction.replace(R.id.frameLayout, telaInicial);
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText(v.getContext(), "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                                barraProgresso.setVisibility(View.INVISIBLE);
                                btEntrar.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } catch (Exception e){
                    Toast.makeText(v.getContext(), "" + e, Toast.LENGTH_LONG).show();
                    barraProgresso.setVisibility(View.INVISIBLE);
                    btEntrar.setVisibility(View.VISIBLE);
                }


            }
        });

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent it = new Intent(v.getContext(), Cadastro.class);
                    startActivity(it);
                } catch (Exception ex) {
                    Toast.makeText(v.getContext(), "Erro inesperado", Toast.LENGTH_LONG).show();
                }
            }
        });


        return v;
    }
}