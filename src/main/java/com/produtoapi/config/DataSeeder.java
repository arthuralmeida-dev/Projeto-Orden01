package com.produtoapi.config;

import com.produtoapi.model.Produto;
import com.produtoapi.model.Usuario;
import com.produtoapi.repository.ProdutoRepository;
import com.produtoapi.repository.UsuarioRepository;
import com.produtoapi.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
@Component
public class DataSeeder implements CommandLineRunner {

    // O método 'run' é a primeira coisa que o Spring executa ao ligar!
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Value("${ADMIN_PASSWORD:123456}")
    private String adminPassword;

    @Value("${OPERADOR_PASSWORD:123456}")
    private String operadorPassword;


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

        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

        // Criação ou Atualização do usuário DONO
        java.util.Optional<Usuario> adminOpt = usuarioRepository.findByEmail("admin@drop21.com");
        if (adminOpt.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("TesteGestão");
            admin.setEmail("admin@drop21.com");
            admin.setSenha(encoder.encode(adminPassword));
            admin.setRole("ROLE_DONO");
            usuarioRepository.save(admin);
        } else {
            Usuario admin = adminOpt.get();
            admin.setSenha(encoder.encode(adminPassword));
            usuarioRepository.save(admin);
        }

        // Criação ou Atualização do usuário OPERADOR (Caixa)
        java.util.Optional<Usuario> operadorOpt = usuarioRepository.findByEmail("operador@drop21.com");
        if (operadorOpt.isEmpty()) {
            Usuario operador = new Usuario();
            operador.setNome("TestePDV");
            operador.setEmail("operador@drop21.com");
            operador.setSenha(encoder.encode(operadorPassword));
            operador.setRole("ROLE_OPERADOR");
            usuarioRepository.save(operador);
        } else {
            Usuario operador = operadorOpt.get();
            operador.setSenha(encoder.encode(operadorPassword));
            usuarioRepository.save(operador);
        }

    }
}