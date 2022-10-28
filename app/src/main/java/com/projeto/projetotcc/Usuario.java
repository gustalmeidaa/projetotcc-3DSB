package com.projeto.projetotcc;

import java.util.List;

public class Usuario {
    private String nome;
    private String email;
    private List<String> alergenicos;

    public Usuario(){}

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAlergenicos() {
        return alergenicos;
    }

    public void setAlergenicos(List<String> alergenicos) {
        this.alergenicos = alergenicos;
    }
}
