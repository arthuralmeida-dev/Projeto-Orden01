package com.produtoapi.service;

import com.produtoapi.model.Produto;
import com.produtoapi.model.Pedido;
import com.produtoapi.model.ItemPedido;
import com.produtoapi.repository.ProdutoRepository;
import com.produtoapi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido criarPedido(String nomeCliente, List<ItemPedido> itens) {

        Pedido pedido = new Pedido();

        pedido.setNomeCliente(nomeCliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setValorTotal(0.0);

        // Para cada "linha" da nota (item) que o cliente enviou...
        for (ItemPedido item : itens) {

            // 1. Busca o produto verdadeiro no banco
            Produto produtoBanco = produtoRepository.findById(item.getProduto().getId()).orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

            // 2. Confere se tem na prateleira
            if (produtoBanco.getQuantidade() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produtoBanco.getNome());
            }

            // 3. Tira da prateleira e salva no banco de dados
            produtoBanco.setQuantidade(produtoBanco.getQuantidade() - item.getQuantidade());
            produtoRepository.save(produtoBanco); // Salva o PRODUTO!

            // 4. Preenche os valores da "linha" da nota (item)
            item.setPrecoUnitario(produtoBanco.getPreco()); // Copia o preço de hoje pro item
            item.setSubTotal(item.getQuantidade() * item.getPrecoUnitario()); // Calcula: Qtde da compra X Preço
            item.setPedido(pedido); // Avisa: "Ei item, você faz parte desta nota (pedido)"

            // 5. Soma esse subtotal no Total da nota
            pedido.setValorTotal(pedido.getValorTotal() + item.getSubTotal());
        }

        // Depois que acabar o for (saiu do laço), salva a "nota" inteira no banco e devolve pro cliente!
        return pedidoRepository.save(pedido);

        /* O Produto (produtoBanco): É o que está na prateleira do banco de dados. Ele tem o controle do ESTOQUE total.
         (Ex: "Temos 100 Teclados na loja").

          O ItemPedido (item): É o carrinho de compras que o cliente preencheu no HTML. É uma "linha" da nota.
         (Ex: "O cliente escolheu 2 Teclados"). A gente usa essa linha para saber o que diminuir lá do produtoBanco.

         O Pedido (pedido): É a nota fiscal final (a "capa"). O HTML não mandou isso pra gente.
         Nós que estamos construindo ela do zero no nosso código Java (new Pedido()).
         Nós colocamos o nome do cliente, calculamos o total de todas as linhas (ItemPedido) e carimbamos a data de hoje.
         No final, nós pegamos essa nota inteira e jogamos no banco de dados.*/
    }
}
