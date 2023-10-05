package br.luahr.topicos1.model;

import jakarta.persistence.Entity;

@Entity
public class Vendedor extends DefaultEntity {

    private String nome;

    private String cpj;

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpj() {
        return cpj;
    }

    public void setCpj(String cpj) {
        this.cpj = cpj;
    }

}
