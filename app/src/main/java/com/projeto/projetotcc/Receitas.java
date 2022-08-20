package com.projeto.projetotcc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Receitas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Receitas extends Fragment {
    View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Receitas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Receitas.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_receitas, container, false);
        Button pesquisar = view.findViewById(R.id.btPesquisar);
        EditText ingrediente = view.findViewById(R.id.txtIngredientes);

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarIngredientes(ingrediente.getText().toString());
            }
        });

        return view;
    }

    private void buscarIngredientes(String ingredienteInserido){
        db.collection("receitas").whereEqualTo("nome", ingredienteInserido).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Toast.makeText(view.getContext().getApplicationContext(), "" + ingredienteInserido, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(view.getContext().getApplicationContext(), "Deu ruim", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex){
                    Toast.makeText(view.getContext().getApplicationContext(), "" + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}