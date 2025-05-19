package com.evaluacion2.Clientes.clientes.controller;

import com.evaluacion2.Clientes.clientes.model.Soporte;
import com.evaluacion2.Clientes.clientes.service.SoporteService;
import com.evaluacion2.Clientes.clientes.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/soporte")
public class SoporteController {
    @Autowired
    private SoporteService soporteService;

    @PostMapping
    public ResponseEntity<Soporte> createSoporte(@RequestBody Soporte soporte) {
        Soporte createdSoporte = soporteService.saveSoporte(soporte);
        return new ResponseEntity<>(createdSoporte, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soporte> getSoporteById(@PathVariable Long id) {
        Soporte soporte = soporteService.getSoporteById(id);
        if (soporte != null) {
            return new ResponseEntity<>(soporte, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Soporte>> getAllSoportes() {
        List<Soporte> soportes = soporteService.getAllSoportes();
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoporte(@PathVariable Long id) {
        soporteService.deleteSoporte(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{estado}")
    public ResponseEntity<List<Soporte>> getSoportesByEstado(@PathVariable String estado) {
        List<Soporte> soportes = soporteService.findByEstado(estado);
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<Soporte>> getSoportesByCliente(@PathVariable Long clienteId) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        List<Soporte> soportes = soporteService.findByCliente(cliente);
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }

    @GetMapping("/fechaCreacion")
    public ResponseEntity<List<Soporte>> getSoportesByFechaCreacion(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<Soporte> soportes = soporteService.findByFechaCreacionBetween(startDate, endDate);
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }
}
