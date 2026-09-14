package com.produtoapi.config;

import com.produtoapi.model.Produto;
import com.produtoapi.repository.ProdutoRepository;
import com.produtoapi.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    // O método 'run' é a primeira coisa que o Spring executa ao ligar!
    @Autowired
    ProdutoRepository produtoRepository;



    @Override
    public void run(String... args) throws Exception {

        System.out.println("O Spring Boot ligou! As portas estão fechadas, hora de arrumar as prateleiras...");

        if(produtoRepository.count() == 0 ){

            Produto produto = new Produto();

            //Criação do produto
            produto.setNome("Mouse Redragon");
            produto.setPreco(89.99);
            produto.setQuantidade(55);
            produto.setStatus("Disponível");

            // Mandando o estoque salvar no banco
            produtoRepository.save(produto);

            Produto produto2 = new Produto();
            produto2.setNome("Teclado Redragon");
            produto2.setPreco(229.99);
            produto2.setQuantidade(75);
            produto2.setStatus("Disponível");

            produtoRepository.save(produto2);


            System.out.println("Produtos cadastrados com sucesso pelo Seeder!");
        }

        // Aqui dentro vai entrar a nossa lógica!
    }
}