package com.projeto.projetotcc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlterarDados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlterarDados extends Fragment {
    View view;
    FirebaseAuth usuario = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText txtEmail;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlterarDados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlterarSenha.
     */
    // TODO: Rename and change types and number of parameters
    public static AlterarDados newInstance(String param1, String param2) {
        AlterarDados fragment = new AlterarDados();
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
        view = inflater.inflate(R.layout.fragment_alterar_dados, container, false);
        txtEmail = view.findViewById(R.id.txtEmail);
        Button btEnviarEmail = view.findViewById(R.id.btAlterarSenha);
        TextView cabecalho = view.findViewById(R.id.txtCabecalho);
        EditText txtSenha = view.findViewById(R.id.txtSenha);
        CardView deletar = view.findViewById(R.id.cardView2);
        Button btDeletar = view.findViewById(R.id.btAlterarDados);
        Button btAlterarSenha = view.findViewById(R.id.btAlterarSenha);

        try{
            //Verificando se há um usuário logado
            if(usuario.getCurrentUser() != null) {
                cabecalho.setText("Alterar Dados");
                btAlterarSenha.setText("Mudar Senha");
                txtEmail.setVisibility(view.INVISIBLE);
                deletar.setVisibility(view.VISIBLE);

                btAlterarSenha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String senha = txtSenha.getText().toString().trim();
                        alterarSenha(senha);
                    }
                });

                //Configurando o botão "Deletar" caso o usuário pressione-o
                btDeletar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder confirmarApagarConta = new AlertDialog.Builder(view.getContext());
                        confirmarApagarConta.setTitle("ATENÇÃO!");
                        confirmarApagarConta.setMessage("Tem certeza que deseja excluir sua conta?");
                        confirmarApagarConta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletarConta();
                            }
                        });
                        confirmarApagarConta.setNegativeButton("Não", null);
                        confirmarApagarConta.create().show();
                    }
                });
            }
             else {

            }
        } catch (Exception o){
            Toast.makeText(view.getContext(), "" + o, Toast.LENGTH_SHORT).show();
        }

        try{
            if(usuario.getCurrentUser() == null){
                txtSenha.setVisibility(view.INVISIBLE);
                btEnviarEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = txtEmail.getText().toString().trim();
                        usuario.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(view.getContext(), "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        } catch (Exception g){
            g.printStackTrace();
        }

        return view;
    }

    private void deletarConta(){
        try{
            String uid = usuario.getCurrentUser().getUid();
            db.collection("usuarios").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.e("FOI >>>> ", "Deu certo");
                }
            });
            usuario.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(view.getContext(), "Conta excluída com sucesso", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e){
            Toast.makeText(view.getContext(), "" + e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void alterarSenha(String senha){
        try{
            usuario.getCurrentUser().updatePassword(senha).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(view.getContext(), "Senha alterada com sucesso", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}