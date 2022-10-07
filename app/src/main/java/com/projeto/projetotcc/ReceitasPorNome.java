package com.projeto.projetotcc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceitasPorNome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceitasPorNome extends Fragment {
    View view;
    private List<String> listaAlergenicos = new ArrayList<>();
    private List<String> listaNomes = new ArrayList<>();
    private List<Receita> lReceitas = new ArrayList<>();
    private String nomeReceita;
    FirebaseAuth autenticador = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceitasPorNome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceitasPorNome.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceitasPorNome newInstance(String param1, String param2) {
        ReceitasPorNome fragment = new ReceitasPorNome();
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
        view = inflater.inflate(R.layout.fragment_receitas_por_nome, container, false);
        Button pesquisar = view.findViewById(R.id.btPesquisar);
        EditText nomeDaReceita = view.findViewById(R.id.edNomeReceita);

        try{
            //Verificando se há um usuário logado
            if(autenticador.getCurrentUser() != null){
                //Atribuindo o 'uid' do documento do usuário logado a variável 'uid'
                uid = autenticador.getUid();
                if(!uid.isEmpty()){
                    db.collection("usuarios").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            Usuario usuario = value.toObject(Usuario.class);
                            if(!(usuario.getAlergenicos() == null)){
                                for(String alerg : usuario.getAlergenicos()){
                                    listaAlergenicos.add(alerg);
                                }
                            }

                        }
                    });
                }
            }
        } catch (Exception o){
            Toast.makeText(view.getContext(), "" + o, Toast.LENGTH_SHORT).show();
        }

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    nomeReceita = nomeDaReceita.getText().toString().trim();
                    //Verificando se o nome inserido não é menor ou igual a 2 caracteres
                    if(!(nomeReceita.length() <= 2)){
                        buscarReceita();
                    } else {
                        Toast.makeText(view.getContext(), "Digite um nome válido", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(view.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
                }
            }

        });


        return view;
    }


    private void buscarReceita(){
        try{
            db.collection("receitas").orderBy("nome").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                ListView listaReceitas = view.findViewById(R.id.listaReceitas);
                List<Receita> listaDasReceitas = new ArrayList<>();
                ArrayAdapter<String> adapter;
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Receita receitas = document.toObject(Receita.class);
                            lReceitas.add(receitas);
                        }

                        try{
                            //Verificando se a lista de alergênicos está vazia
                            if (listaAlergenicos.isEmpty()) {
                                //Se estiver, apenas o valor do nome inserido será
                                //analisado em busca de receitas
                                listaDasReceitas = lReceitas.stream().filter(nomesReceitas -> nomesReceitas.nomesReceitas.stream().anyMatch(s -> s.contains(nomeReceita)))
                                        .collect(Collectors.toList());
                                //lReceitas = listaDasReceitas;
                                Toast.makeText(view.getContext(), "" + listaDasReceitas.get(0).toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(view.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } catch (Exception ex){
            Toast.makeText(view.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
        }
    }
}