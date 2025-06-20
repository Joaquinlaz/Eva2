package com.ecomarket.ecomarket.Controller;

import com.ecomarket.ecomarket.Model.Pedido;
import com.ecomarket.ecomarket.Service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtener todos los pedidos",
            description = "Devuelve una lista de todos los pedidos registrados en el sistema."
    )
    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return service.obtenerPedidos();
    }

    @Operation(
            summary = "Crear un nuevo pedido",
            description = "Crea un nuevo pedido basado en los datos proporcionados en el cuerpo de la solicitud."
    )
    @PostMapping
    public Pedido crearPedido(
            @Parameter(description = "Detalles del pedido que se va a crear.") 
            @RequestBody Pedido pedido) {
        return service.guardarPedido(pedido);
    }

    @Operation(
            summary = "Eliminar un pedido",
            description = "Elimina un pedido identificado por su ID."
    )
    @DeleteMapping("/{id}")
    public void eliminarPedido(
            @Parameter(description = "ID del pedido que se desea eliminar.") 
            @PathVariable Long id) {
        service.eliminarPedido(id);
    }
}
