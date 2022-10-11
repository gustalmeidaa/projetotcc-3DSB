package com.projeto.projetotcc;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUsuario extends Fragment {
    View v;
    private List<String> listaAlergenicos = new ArrayList<>();
    private ChipGroup grupoIngredientes;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilUsuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUsuario newInstance(String param1, String param2) {
        PerfilUsuario fragment = new PerfilUsuario();
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
        v = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);
        Button sair = v.findViewById(R.id.btSair);
        TextView nomeUsuario = v.findViewById(R.id.edNomeUsuario);
        EditText edAlergenico = v.findViewById(R.id.edAlergenico);
        ChipGroup grupoIngredientes = v.findViewById(R.id.grupoIngredientes);
        Button adicionarChip = v.findViewById(R.id.btAdicionarChip);
        Button btAlterarDados = v.findViewById(R.id.btAlterarDados);

        //Chamando o método exibir alergênicos para mostrar ao usuário o que já foi cadastrado
        //no banco de dados
        exibirAlergenicos();

        try{
            db.collection("usuarios").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value != null){
                        nomeUsuario.setText(value.getString("nome"));
                    }
                }
            });
        } catch (Exception ex){
            Toast.makeText(v.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
        }

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Capturando a instância e realizando o processo de "deslogar" da conta
                FirebaseAuth.getInstance().signOut();
                Autenticacao autenticacao = new Autenticacao();

                //Retornando ao Fragment da página de "Autenticação"
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.frameLayout, autenticacao, null).commit();
            }
        });

        btAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarDados alterarDados = new AlterarDados();

                //Direcionando ao Fragment da página de "Alterar Dados"
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.frameLayout, alterarDados, null).addToBackStack(null).commit();
            }
        });

        try{
            adicionarChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ingredientes = edAlergenico.getText().toString().trim().toLowerCase();
                    if(!(ingredientes.length() <= 2)){
                        //Verificando se o ingrediente inserido ainda não está na lista de alergênicos
                        if(!(listaAlergenicos.contains(ingredientes))){
                            estruturarChip(ingredientes);
                            edAlergenico.setText("");
                            listaAlergenicos.add(ingredientes);
                            //Atualizando a lista de alergênicos
                            atualizarAlergenicos(listaAlergenicos);
                        } else {
                            Toast.makeText(v.getContext(), "Este ingrediente já foi inserido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Adicione um ingrediente válido", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex){
            Toast.makeText(v.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
        }
        return v;
    }


    private void exibirAlergenicos(){
        db.collection("usuarios").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try{
                    if(value.exists()){
                        //Se o valor existir, será criado um objeto da classe "Usuario"
                        //a partir da desserialização obtida no listener
                        Usuario usuario = value.toObject(Usuario.class);
                        //Capturando cada alergênico declarado no objeto "usuario"
                        if(!(usuario.getAlergenicos() == null)){
                            for(String alerg : usuario.getAlergenicos()){
                                //Verificando se a lista já não possui o alergênico inserido
                                if(!(listaAlergenicos.contains(alerg))){
                                    estruturarChip(alerg);
                                    listaAlergenicos.add(alerg);
                                }
                            }
                        }

                    }
                } catch (Exception ex){
                    Toast.makeText(v.getContext(), "ERRO: " + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void estruturarChip(String ingredientes){
        try{
            grupoIngredientes = v.findViewById(R.id.grupoIngredientes);
            Chip chip = new Chip(v.getContext());
            chip.setText(ingredientes);
            chip.setChipIconResource(R.drawable.ic_receitas);
            chip.setTextColor(ColorStateList.valueOf(Color.BLACK));
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(168, 142, 114)));
            chip.setCloseIconVisible(true);
            chip.setX(30);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    grupoIngredientes.removeView(chip);
                    //Capturando o texto do Chip para remover e atualizar a lista de alergênicos
                    //no banco de dados
                    String removerAlergenico = chip.getText().toString();
                    listaAlergenicos.remove(removerAlergenico);
                    atualizarAlergenicos(listaAlergenicos);
                }
            });
            grupoIngredientes.addView(chip);
        } catch (Exception ex){
            Toast.makeText(v.getContext(), "" + ex , Toast.LENGTH_SHORT).show();

        }
    }

    private void atualizarAlergenicos(List<String> listaAlergenicos){
        //Atualizando a lista de alergênicos no banco de dados
        try{
            db.collection("usuarios").document(uid).update("alergenicos", listaAlergenicos);
        } catch (Exception ex){
            Toast.makeText(v.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
        }

    }

}