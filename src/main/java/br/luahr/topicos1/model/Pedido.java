package br.luahr.topicos1.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido extends DefaultEntity {

    @ManyToMany
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private LocalDateTime dataPedido;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "pedido")
    private List<Compra> itens;

    private Double totalPedido;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<Compra> getItens() {
        return itens;
    }

    public void setItens(List<Compra> itens) {
        this.itens = itens;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

}
