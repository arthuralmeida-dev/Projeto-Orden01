package com.produtoapi.repository;

import com.produtoapi.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    // Método extra para achar se o operador tem um caixa aberto
    Optional<Caixa> findByOperadorNomeAndStatus(String operadorNome, String status);
}