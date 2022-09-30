package com.projeto.projetotcc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TelaReceita extends Fragment {

    View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TelaReceita() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TelaReceita newInstance(String param1, String param2) {
        TelaReceita fragment = new TelaReceita();
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
        try{
            view = inflater.inflate(R.layout.fragment_tela_receita, container, false);
            //Criando os objetos de cada componente da tela
            Switch alternarModo = view.findViewById(R.id.swIngModo);
            TextView txtNomeReceita = view.findViewById(R.id.txtNomeReceita);
            TextView nomeSwitch = view.findViewById(R.id.txtNomeSwitch);
            ListView listarInformacoes = view.findViewById(R.id.listarInformacoes);

            //Passando os argumentos através do getArguments() do Bundle
            Bundle bundle = getArguments();
            List<String> modo = bundle.getStringArrayList("modoPreparo");
            List<String> ingredientes = bundle.getStringArrayList("listaIngredientes");
            String nome = bundle.getString("nome");
            txtNomeReceita.setText(nome);

            nomeSwitch.setText("    Ingredientes");
            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                    R.layout.layout_lista,
                    ingredientes);
            listarInformacoes.setAdapter(adapter);



            //Método para alternar a lista entre "Ingredientes" e "Modo de Preparo"
            alternarModo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //Verificando qual é a seleção do usuário
                    //Caso o switch não esteja "selecionado", serão listados os ingredientes
                    if(!alternarModo.isChecked()){
                        nomeSwitch.setText("    Ingredientes");
                        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                                R.layout.layout_lista,
                                ingredientes);
                        listarInformacoes.setAdapter(adapter);
                        listarInformacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String item = (String) adapterView.getItemAtPosition(i);
                            }
                        });
                    } else {
                        //Caso o switch esteja "selecionado", será listado o modo de preparo.
                        nomeSwitch.setText("Modo de Preparo");
                        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),
                                R.layout.layout_lista,
                                modo);
                        listarInformacoes.setAdapter(adapter);
                    }
                }
            });

        } catch (Exception ex){
            Toast.makeText(getContext(), "" + ex, Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}