package com.produtoapi.repository;

import com.produtoapi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
