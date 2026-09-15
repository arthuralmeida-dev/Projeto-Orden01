package com.produtoapi.repository;

import com.produtoapi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    java.util.List<Pedido> findByEmailCliente(String emailCliente);

}
