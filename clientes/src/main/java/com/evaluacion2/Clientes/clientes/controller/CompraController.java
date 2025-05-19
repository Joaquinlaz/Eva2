package com.evaluacion2.Clientes.clientes.controller;

import com.evaluacion2.Clientes.clientes.model.Cliente;
import com.evaluacion2.Clientes.clientes.service.CompraService;
import com.evaluacion2.Clientes.clientes.model.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    // Get todas las compras
    @GetMapping
    public ResponseEntity<List<Compra>> getAllCompras() {
        List<Compra> compras = compraService.getAllCompras();
        if (compras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable Long id) {
        Compra compra = compraService.getCompraById(id);
        if (compra != null) {
            return new ResponseEntity<>(compra, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Compra>> getComprasByClienteId(@PathVariable Long clienteId) {
        List<Compra> compras = compraService.findByClienteId(clienteId);
        if (compras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<Compra> createCompra(@PathVariable Long clienteId, @RequestBody Compra compra) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        compra.setCliente(cliente);
        Compra createdCompra = compraService.saveCompra(compra);
        return new ResponseEntity<>(createdCompra, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Compra> updateCompra(@PathVariable Long id, @RequestBody Compra compra) {
        Compra existingCompra = compraService.getCompraById(id);
        if (existingCompra != null) {
            existingCompra.setPrecioTotal(compra.getPrecioTotal());
            existingCompra.setFechaCompra(compra.getFechaCompra());
            Compra updatedCompra = compraService.saveCompra(existingCompra);
            return new ResponseEntity<>(updatedCompra, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/precioTotal")
    public ResponseEntity<List<Compra>> getComprasByPrecioTotal(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<Compra> compras = compraService.findByPrecioTotalBetween(minPrice, maxPrice);
        if (compras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/fechaCompra")
    public ResponseEntity<List<Compra>> getComprasByFechaCompra(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<Compra> compras = compraService.findByFechaCompraBetween(startDate, endDate);
        if (compras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        Compra existingCompra = compraService.getCompraById(id);
        if (existingCompra != null) {
            compraService.deleteCompra(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
