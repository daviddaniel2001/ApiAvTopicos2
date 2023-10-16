package br.luahr.topicos1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Transportadora extends DefaultEntity {

    private String nome;

    @ManyToOne
    private Telefone telefone;

    @ManyToOne
    private Endereco endereco;

    private String metodoEntrega;

    private Integer valorEntrega;

    private String tempoEstimado;

    @ManyToOne
    private Compra compra;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getMetodoEntrega() {
        return metodoEntrega;
    }

    public void setMetodoEntrega(String metodoEntrega) {
        this.metodoEntrega = metodoEntrega;
    }

    public Integer getValorEntrega() {
        return valorEntrega;
    }

    public void setValorEntrega(Integer valorEntrega) {
        this.valorEntrega = valorEntrega;
    }

    public String getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(String tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

}
