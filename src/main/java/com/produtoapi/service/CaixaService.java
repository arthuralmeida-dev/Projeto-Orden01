package com.produtoapi.service;

import com.produtoapi.model.*;
import com.produtoapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private VendaBalcaoRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository; // Pra gente baixar o estoque!

    // 1. ABRIR CAIXA
    public Caixa abrirCaixa(String operadorNome, double valorInicial) {
        // Verifica se ele já não tem um caixa aberto (não pode abrir 2 ao mesmo tempo)
        if (caixaRepository.findByOperadorNomeAndStatus(operadorNome, "ABERTO").isPresent()) {
            throw new RuntimeException("Este operador já possui um caixa aberto!");
        }

        Caixa novoCaixa = new Caixa();
        novoCaixa.setOperadorNome(operadorNome);
        novoCaixa.setValorInicial(valorInicial);
        novoCaixa.setStatus("ABERTO");
        novoCaixa.setTotalVendas(0.0);
        novoCaixa.setAbertoEm(LocalDateTime.now());

        return caixaRepository.save(novoCaixa);
    }

    // 2. REGISTRAR VENDA
    public VendaBalcao registrarVenda(Long caixaId, String formaPagamento, double desconto, List<ItemVendaBalcao> itens) {

        // 1. Acha o caixa
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));

        if (!caixa.getStatus().equals("ABERTO")) {
            throw new RuntimeException("O caixa está fechado!");
        }

        VendaBalcao venda = new VendaBalcao();
        venda.setCaixa(caixa);
        venda.setFormaPagamento(formaPagamento);
        venda.setDesconto(desconto);
        venda.setCriadaEm(LocalDateTime.now());
        venda.setTotal(0.0);

        // BAIXA DE ESTOQUE
        for (ItemVendaBalcao item : itens) {
            Produto produtoBanco = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

            if (produtoBanco.getQuantidade() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para: " + produtoBanco.getNome());
            }

            // Baixa o estoque (mesma coisa da venda online!)
            produtoBanco.setQuantidade(produtoBanco.getQuantidade() - item.getQuantidade());

            // Quando estoque zerar, o estado do estoque muda para sem estoque
            if (produtoBanco.getQuantidade() == 0) {
                produtoBanco.setStatus("Sem Estoque");
            }
            produtoRepository.save(produtoBanco);

            // Prepara o item
            item.setPrecoUnitario(produtoBanco.getPreco());
            // Subtotal = (Preço * Qtd) - descontoItem
            item.setSubtotal((item.getPrecoUnitario() * item.getQuantidade()) - item.getDescontoItem());
            item.setVenda(venda);

            venda.setTotal(venda.getTotal() + item.getSubtotal());
        }

        // Aplica o desconto geral no total da venda
        venda.setTotal(venda.getTotal() - desconto);

        // Soma essa venda no total do Caixa do dia
        caixa.setTotalVendas(caixa.getTotalVendas() + venda.getTotal());
        caixaRepository.save(caixa);

        return vendaRepository.save(venda);
    }

    // 3. FECHAR CAIXA
    public Caixa fecharCaixa(Long caixaId) {
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));

        caixa.setStatus("FECHADO");
        caixa.setFechadoEm(LocalDateTime.now());

        return caixaRepository.save(caixa);
    }
}