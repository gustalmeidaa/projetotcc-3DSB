package com.projeto.projetotcc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private FirebaseAuth autenticador = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String uid;

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
                        listaNomes.add(nomeReceita);
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
                List<String> nomesReceitas = new ArrayList<>();
                List<Receita> listaFinal = new ArrayList<Receita>();
                List<List<String>> modo_Preparo = new ArrayList<List<String>>();
                List<List<String>> ingredientes = new ArrayList<List<String>>();
                ArrayAdapter<String> adapter;
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Receita receitas = document.toObject(Receita.class);
                            lReceitas.add(receitas);
                        }

                        try{
                            //Analisando a lista de receitas em busca do nome informado
                            for (Receita rec : lReceitas) {
                                if (rec.getNome().contains(nomeReceita)) {
                                    listaDasReceitas.add(rec);
                                }
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(view.getContext(), "" + e, Toast.LENGTH_SHORT).show();
                        }

                        for (Receita rec : listaDasReceitas) {
                            ingredientes.add(rec.ingredientes);
                            modo_Preparo.add(rec.modoPreparo);
                            nomesReceitas.add(rec.nome);
                        }

                        //Inicialização do adapter e atribuir a lista dos nomes
                        adapter = new ArrayAdapter<String>(getContext(),
                                R.layout.layout_lista,
                                nomesReceitas);

                        //Adicionar os itens na lista usando o método setAdapter
                        listaReceitas.setAdapter(adapter);


                        //Adicionar os itens na lista usando o método setAdapter
                        listaReceitas.setAdapter(adapter);

                        //Listener para abrir as receitas numa outra tela ao clicar
                        listaReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //Criando as variáveis que irão guardar as informações
                                //da receita de acordo com o índice delas na lista
                                String nomeReceita = nomesReceitas.get(i);
                                List<String> modoPreparo = modo_Preparo.get(i);
                                List<String> listaIngredientes = ingredientes.get(i);

                                //Criando os objetos do Fragment e do Bundle
                                TelaReceita telaReceita = new TelaReceita();
                                Bundle bundle = new Bundle();

                                //Atribuindo valores às variáveis do bundle
                                bundle.putString("nome", nomeReceita);
                                bundle.putStringArrayList("modoPreparo", (ArrayList<String>) modoPreparo);
                                bundle.putStringArrayList("listaIngredientes", (ArrayList<String>) listaIngredientes);
                                telaReceita.setArguments(bundle);

                                //Setando o novo Fragment
                                FragmentManager manager = getFragmentManager();
                                manager.beginTransaction().replace(R.id.frameLayout, telaReceita, null).addToBackStack(null).commit();
                            }
                        });

                        //Limpando a lista de receitas após a pesquisa
                        lReceitas.clear();
                    }
                }
            });
        } catch (Exception ex){
            Toast.makeText(view.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
        }
    }
}