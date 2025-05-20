package com.ecomarket.inventario.controller;

import com.ecomarket.inventario.model.Pedido;
import com.ecomarket.inventario.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }

    @PatchMapping("/{id}")
    public Pedido partialUpdatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido existingPedido = pedidoService.findById(id);
        if (existingPedido != null) {
            if (pedido.getProductos() != null) {
                existingPedido.setProductos(pedido.getProductos());
            }
            if (pedido.getEstado() != null) {
                existingPedido.setEstado(pedido.getEstado());
            }
            if (pedido.getProductos() != null) {
                existingPedido.setProductos(pedido.getProductos());
            }
            return pedidoService.save(existingPedido);
        }
        return null;
    }

    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
    }
}