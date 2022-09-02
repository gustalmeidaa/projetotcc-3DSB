package com.projeto.projetotcc;

import static android.graphics.Color.rgb;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Receitas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Receitas extends Fragment {
    View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> listaIngredientes = new ArrayList<>();
    private List<Receita> lReceitas = new ArrayList<>();
    private ChipGroup grupoIngredientes;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Receitas() {
    }

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

        adicionarChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientes = ingrediente.getText().toString().trim();
                if(!(ingredientes.length() <= 2)){
                    estruturarChip(ingredientes);
                    ingrediente.setText("");
                } else {
                    Toast.makeText(getContext(), "Adicione um ingrediente válido", Toast.LENGTH_SHORT).show();
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
                    buscarReceitas(listaIngredientes);
                } catch (Exception ex){
                    Toast.makeText(getContext(), "" + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void estruturarChip(String ingredientes){
        try{
            Chip chip = new Chip(getContext());
            chip.setText(ingredientes);
            chip.setChipIconResource(R.drawable.ic_receitas);
            chip.setTextColor(ColorStateList.valueOf(Color.BLACK));
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.rgb(255, 183, 2)));
            chip.setCloseIconVisible(true);
            chip.setX(30);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    grupoIngredientes.removeView(chip);
                }
            });
            grupoIngredientes = view.findViewById(R.id.grupoIngredientes);
            grupoIngredientes.addView(chip);
        } catch (Exception ex){
            Toast.makeText(getContext(), "" + ex, Toast.LENGTH_SHORT).show();

        }
    }

    private void buscarReceitas(List<String> listaIngredientes) {
        try{
            if (!listaIngredientes.isEmpty()) {
                //Acessando o banco de dados no Firestore em busca da coleção de "receitas"
                db.collection("receitas").orderBy("ingredientes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ListView listaReceitas = view.findViewById(R.id.listaReceitas);
                    ArrayAdapter<String> adapter;
                    List<Receita> listaDasReceitas;
                    List<String> nomesReceitas = new ArrayList<>();
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
                                    String comparacao = chip.getText().toString();
                                    listaDasReceitas = lReceitas.stream().filter(ingredientes -> ingredientes.ingredientes.stream().anyMatch(s -> s.contains(comparacao)))
                                            .collect(Collectors.toList());
                                    lReceitas = listaDasReceitas;
                                }

                                for (Receita rec : listaDasReceitas) {
                                    nomesReceitas.add(rec.nome);
                                }

                                //Inicialização do adapter e atribuir a lista dos nomes
                                adapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_list_item_1,
                                        nomesReceitas);

                                //Adicionar os itens na lista usando o método setAdapter
                                listaReceitas.setAdapter(adapter);

                                //Limpando a lista de ingredientes após a finalização do método
                                listaIngredientes.clear();
                                //Limpando a lista de receitas após a pesquisa
                                lReceitas.clear();
                            } else {
                                Toast.makeText(view.getContext().getApplicationContext(), "Insira ao menos 1 (um) ingrediente", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(view.getContext().getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (Exception ex){
            Toast.makeText(view.getContext().getApplicationContext(), "TESTE" + ex, Toast.LENGTH_SHORT).show();
        }
    }
}