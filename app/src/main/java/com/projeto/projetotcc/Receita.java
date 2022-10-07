package com.projeto.projetotcc;


import java.util.List;

public class Receita {
    protected String nome;
    protected List<String> ingredientes;
    protected List<String> nomesReceitas;
    protected List<String> modoPreparo;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getNomesReceitas() {
        return nomesReceitas;
    }

    public void setNomesReceitas(List<String> NomesReceitas) {
        this.nomesReceitas = nomesReceitas;
    }

    public List<String> getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(List<String> modoPreparo) {
        this.modoPreparo = modoPreparo;
    }
}
