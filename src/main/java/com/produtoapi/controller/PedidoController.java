package com.produtoapi.controller;

import com.produtoapi.model.ItemPedido;
import com.produtoapi.model.Pedido;
import com.produtoapi.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Método que o HTML vai chamar
    @PostMapping
    public Pedido realizarPedido(@RequestBody PedidoRequest request) {
        // AQUI CHAMA O SERVICE!
        return pedidoService.criarPedido(request.getNomeCliente(), request.getEmailCliente(), request.getFormaPagamento(), request.getItens());
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    // Busca os pedidos de um cliente pelo e-mail
    @GetMapping("/cliente")
    public List<Pedido> listarPorCliente(@RequestParam String email) {
        return pedidoService.listarPorCliente(email);
    }

    // Cancela um pedido e devolve o estoque
    @PutMapping("/{id}/cancelar")
    public Pedido cancelarPedido(@PathVariable Long id) {
        return pedidoService.cancelarPedido(id);
    }

    // Endpoint para pagar um pedido
    @PutMapping("/{id}/pagar")
    public Pedido pagarPedido(@PathVariable Long id, @RequestBody PagamentoRequest pagamento) {
        return pedidoService.pagarPedido(id, pagamento.getFormaPagamento());
    }


    // Classe auxiliar que representa exatamente o formato do JSON que o HTML vai mandar
    public static class PedidoRequest {
        private String nomeCliente;
        private String emailCliente;
        private String formaPagamento;
        private List<ItemPedido> itens;

        // Getters e Setters do PedidoRequest
        public String getNomeCliente() {
            return nomeCliente;
        }

        public void setNomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
        }
        public List<ItemPedido> getItens() {
            return itens;
        }

        public void setItens(List<ItemPedido> itens) {
            this.itens = itens;
        }

        public String getFormaPagamento() {
            return formaPagamento;
        }

        public void setFormaPagamento(String formaPagamento) {
            this.formaPagamento = formaPagamento;
        }

        public String getEmailCliente() {
            return emailCliente;
        }

        public void setEmailCliente(String emailCliente) {
            this.emailCliente = emailCliente;
        }


    }

    // Classe que recebe o pagamento do Javascript
    public static class PagamentoRequest {
        private String formaPagamento;

        public String getFormaPagamento() {
            return formaPagamento;
        }

        public void setFormaPagamento(String formaPagamento) {
            this.formaPagamento = formaPagamento;
        }
    }

}