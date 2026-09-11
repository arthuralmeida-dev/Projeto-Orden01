package com.produtoapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operadorNome;      // Nome do operador que abriu o caixa
    private double valorInicial;      // Troco inicial (ex: R$ 200)
    private double totalVendas;       // Soma de todas as vendas (começa em 0)
    private String status;            // ABERTO ou FECHADO
    private LocalDateTime abertoEm;   // Quando abriu
    private LocalDateTime fechadoEm;  // Quando fechou (null enquanto está aberto)

    public Caixa() {}

    // Getters e Setters de TODOS os campos acima

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperadorNome(String operadorNome){
        this.operadorNome = operadorNome;
    }

    public String getOperadorNome(){
        return operadorNome;
    }

    public void setValorInicial(double valorInicial){
        this.valorInicial = valorInicial;
    }

    public double getValorInicial(){
        return valorInicial;
    }

    public void setTotalVendas(double totalVendas){
        this.totalVendas = totalVendas;
    }

    public double getTotalVendas(){
        return totalVendas;
    }


    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public void setAbertoEm(LocalDateTime abertoEm){
        this.abertoEm = abertoEm;
    }

    public LocalDateTime getAbertoEm(){
        return abertoEm;
    }

    public void setFechadoEm(LocalDateTime fechadoEm){
        this.fechadoEm = fechadoEm;
    }

    public LocalDateTime getFechadoEm(){
        return fechadoEm;
    }
}