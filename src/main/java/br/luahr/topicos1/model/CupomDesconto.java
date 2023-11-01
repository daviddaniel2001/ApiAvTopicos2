package br.luahr.topicos1.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class CupomDesconto extends DefaultEntity{

    private String codigo;

    private String valorDesconto;

    private LocalDate validade;

    @OneToOne
    private Compra compra;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

}
