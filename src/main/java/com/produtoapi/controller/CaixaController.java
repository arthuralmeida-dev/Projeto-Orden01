package com.produtoapi.controller;

import com.produtoapi.model.Caixa;
import com.produtoapi.model.ItemVendaBalcao;
import com.produtoapi.model.VendaBalcao;
import com.produtoapi.service.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    // Rota 1: Abrir o Caixa
    @PostMapping("/abrir")
    public Caixa abrirCaixa(@RequestBody AbrirCaixaRequest request) {
        return caixaService.abrirCaixa(request.getOperadorNome(), request.getValorInicial());
    }

    // Rota 2: Fechar o Caixa
    @PutMapping("/{id}/fechar")
    public Caixa fecharCaixa(@PathVariable Long id) {
        return caixaService.fecharCaixa(id);
    }

    // Rota 3: Registrar Venda
    @PostMapping("/{caixaId}/venda")
    public VendaBalcao registrarVenda(@PathVariable Long caixaId, @RequestBody VendaRequest request) {
        return caixaService.registrarVenda(caixaId, request.getFormaPagamento(), request.getDesconto(), request.getItens());
    }

    // Rota 4: Lista todas as vendas do balcão
    @Autowired
    private com.produtoapi.repository.VendaBalcaoRepository vendaRepository;

    @GetMapping("/vendas")
    public List<VendaBalcao> listarVendasBalcao() {
        return vendaRepository.findAll();
    }

    // --- CLASSES AUXILIARES (Para ler o JSON que vem do HTML) ---

    public static class AbrirCaixaRequest {
        private String operadorNome;
        private double valorInicial;

        public String getOperadorNome() { return operadorNome; }
        public void setOperadorNome(String operadorNome) { this.operadorNome = operadorNome; }
        public double getValorInicial() { return valorInicial; }
        public void setValorInicial(double valorInicial) { this.valorInicial = valorInicial; }
    }

    public static class VendaRequest {
        private String formaPagamento;
        private double desconto;
        private List<ItemVendaBalcao> itens;

        public String getFormaPagamento() { return formaPagamento; }
        public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
        public double getDesconto() { return desconto; }
        public void setDesconto(double desconto) { this.desconto = desconto; }
        public List<ItemVendaBalcao> getItens() { return itens; }
        public void setItens(List<ItemVendaBalcao> itens) { this.itens = itens; }
    }
}