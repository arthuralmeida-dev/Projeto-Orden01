package com.produtoapi.model;

import jakarta.persistence.*;

@Entity
public class ItemVendaBalcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private VendaBalcao venda;        // A qual venda esse item pertence

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;          // Qual produto foi vendido

    private int quantidade;
    private double precoUnitario;
    private double descontoItem;      // Desconto específico deste item
    private double subtotal;

    public ItemVendaBalcao() {}

    // Getters e Setters de TODOS os campos acima


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VendaBalcao getVenda() {
        return venda;
    }

    public void setVenda(VendaBalcao venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getDescontoItem() {
        return descontoItem;
    }

    public void setDescontoItem(double descontoItem) {
        this.descontoItem = descontoItem;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}