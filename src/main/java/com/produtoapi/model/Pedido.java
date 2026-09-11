package com.produtoapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;
    private LocalDateTime dataPedido;
    private double valorTotal;

    /*public Pedido(Long id, String nomeCliente, LocalDateTime dataPedido, double valorTotal ){
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
    }
    */

    // Construtor vazio (obrigatório pro Spring)
    public Pedido() {

    }

    // ---- Getters e Setters aqui em baixo ----


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataPedido(){
        return dataPedido;
    }

    public void setValorTotal(Double valorTotal){
        this.valorTotal = valorTotal;
    }

    public double getValorTotal() {
            return valorTotal;
    }

    private String formaPagamento;

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}