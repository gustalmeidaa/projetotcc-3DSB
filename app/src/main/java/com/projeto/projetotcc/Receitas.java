package com.projeto.projetotcc;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
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

public class Receitas extends Fragment {
    View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> listaIngredientes = new ArrayList<>();
    private List<Receita> lReceitas = new ArrayList<>();
    private List<String> lAlergenicos = new ArrayList<>();
    private ChipGroup grupoIngredientes;
    private List<String> listaAlergenicos = new ArrayList<>();
    FirebaseAuth autenticador = FirebaseAuth.getInstance();
    String uid;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Receitas() {}

    public static Receitas newInstance(String param1, String param2) {
        Receitas fragment = new Receitas();
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

        //Criando os objetos de cada componente da tela
        view = inflater.inflate(R.layout.fragment_receitas, container, false);
        Button pesquisar = view.findViewById(R.id.btPesquisar);
        Button adicionarChip = view.findViewById(R.id.btAdicionarChip);
        EditText ingrediente = view.findViewById(R.id.txtIngredientes);
        ChipGroup grupoIngredientes = view.findViewById(R.id.grupoIngredientes);
        TextView pesquisarPorNome = view.findViewById(R.id.txtPesquisaPorNome);

        pesquisarPorNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceitasPorNome receitasPorNome = new ReceitasPorNome();
                //Setando o novo Fragment
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.frameLayout, receitasPorNome, null).addToBackStack(null).commit();
            }
        });

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

        //Método responsável pela adição do chip na tela
        adicionarChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Capturando o valor do input de ingrediente do usuário
                String ingredientes = ingrediente.getText().toString().trim().toLowerCase();
                //Verificando se o ingrediente inserido não é menor ou igual a 2 caracteres
                if(!(ingredientes.length() <= 2)){
                    //Caso não seja, há a verificação na lista de ingredientes se o que foi digitado
                    //já não está presente
                    if(!listaIngredientes.contains(ingredientes)){
                        estruturarChip(ingredientes);
                        listaIngredientes.add(ingredientes);
                        ingrediente.setText("");
                    } else {
                        Toast.makeText(view.getContext(), "Este ingrediente já foi inserido", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(view.getContext(), "Adicione um ingrediente válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String ingredientes = "";
                    for(int i = 0; i < grupoIngredientes.getChildCount(); i++){
                        Chip chip = (Chip) grupoIngredientes.getChildAt(i);
                        ingredientes = chip.getText().toString();
                        listaIngredientes.add(ingredientes);
                    }
                    buscarReceitas();
                } catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(view.getContext(), "" + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void estruturarChip(String ingredientes){
        try{
            grupoIngredientes = view.findViewById(R.id.grupoIngredientes);
            Chip chip = new Chip(getContext());
            chip.setText(ingredientes);
            chip.setChipIconResource(R.drawable.ic_receitas);
            chip.setTextColor(ColorStateList.valueOf(Color.BLACK));
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(168, 142, 114)));
            chip.setCloseIconVisible(true);
            chip.setX(30);
            //Método para remover o chip do ingrediente caso o "x" seja pressionado
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    grupoIngredientes.removeView(chip);
                }
            });
            grupoIngredientes.addView(chip);
        } catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(view.getContext(), "" + ex, Toast.LENGTH_SHORT).show();

        }
    }

    public void buscarReceitas() {
        try{
            if (!listaIngredientes.isEmpty()) {
                //Acessando o banco de dados no Firestore em busca da coleção de "receitas"
                db.collection("receitas").orderBy("ingredientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ListView listaReceitas = view.findViewById(R.id.listaReceitas);
                    ArrayAdapter<String> adapter;
                    List<Receita> listaDasReceitas = new ArrayList<>();
                    List<String> nomesReceitas = new ArrayList<>();
                    List<List<String>> modo_Preparo = new ArrayList<List<String>>();
                    List<List<String>> ingredientes = new ArrayList<List<String>>();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Receita receitas = document.toObject(Receita.class);
                                    lReceitas.add(receitas);
                                }

                                for(int i = 0; i < grupoIngredientes.getChildCount(); i++){
                                    Chip chip = (Chip) grupoIngredientes.getChildAt(i);
                                    //Verificando se a lista de alergênicos está vazia
                                    if(listaAlergenicos.isEmpty()){
                                        //Se estiver, apenas o valor do ingrediente inserido será
                                        //analisado em busca de receitas
                                        String comparacao = chip.getText().toString().toLowerCase();
                                        listaDasReceitas = lReceitas.stream().filter(ingredientes -> ingredientes.ingredientes.stream().anyMatch(s -> s.contains(comparacao)))
                                                .collect(Collectors.toList());
                                        lReceitas = listaDasReceitas;
                                        if(lReceitas.isEmpty()){
                                            Toast.makeText(view.getContext().getApplicationContext(), "Nenhuma receita encontrada", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        //Caso não esteja vazia, será percorrida a lista de alergênicos
                                        for(int a = 0; a < listaAlergenicos.size(); a++){
                                            //Atribuindo os alergênicos a variável "alergenico"
                                            String alergenico = listaAlergenicos.get(a);
                                            String comparacao = chip.getText().toString().toLowerCase();
                                            //Como feito inicialmente, a lista estará recebendo os valores
                                            //de todas as receitas contendo o(s) ingrediente(s) inseridos
                                            listaDasReceitas = lReceitas.stream().filter(ingredientes -> ingredientes.ingredientes.stream().anyMatch(s -> s.contains(comparacao)))
                                                    .collect(Collectors.toList());
                                            lReceitas = listaDasReceitas;
                                            //Agora, será feita mais uma etapa da filtragem, onde
                                            //serão removidas as recceitas que contenham algum alergênico
                                            listaDasReceitas = lReceitas.stream().filter(ingredientes -> ingredientes.ingredientes.stream().noneMatch(s -> s.contains(alergenico)))
                                                    .collect(Collectors.toList());
                                            lReceitas = listaDasReceitas;
                                            if(lReceitas.isEmpty()){
                                                Toast.makeText(view.getContext().getApplicationContext(), "Nenhuma receita encontrada", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }

                                //Percorrendo a lista das receitas através de um forEach
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

                                //Limpando a lista de ingredientes após a finalização do método
                                listaIngredientes.clear();

                                //Limpando a lista de receitas após a pesquisa
                                lReceitas.clear();
                            } else {
                                Toast.makeText(view.getContext().getApplicationContext(), "Nenhuma receita encontrada", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(view.getContext().getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(view.getContext(), "Digite pelo menos 1 (um) ingrediente.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(view.getContext().getApplicationContext(), "TESTE" + ex, Toast.LENGTH_SHORT).show();
        }
    }
}