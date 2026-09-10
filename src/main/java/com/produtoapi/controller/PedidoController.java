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
        return pedidoService.criarPedido(request.getNomeCliente(), request.getItens());
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    // Classe auxiliar que representa exatamente o formato do JSON que o HTML vai mandar
    public static class PedidoRequest {
        private String nomeCliente;
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

    }
}