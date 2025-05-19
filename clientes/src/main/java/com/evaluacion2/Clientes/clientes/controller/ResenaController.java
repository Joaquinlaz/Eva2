package com.evaluacion2.Clientes.clientes.controller;

import com.evaluacion2.Clientes.clientes.model.Cliente;
import com.evaluacion2.Clientes.clientes.service.ResenaService;
import com.evaluacion2.Clientes.clientes.model.Resena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evaluacion2.Clientes.clientes.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {
    @Autowired
    private ResenaService resenaService;
    @Autowired
    private ClienteService clienteService;
    // Get todas las reseñas
    @GetMapping
    public ResponseEntity<List<Resena>> getAllResenas() {
        List<Resena> resenas = resenaService.getAllResenas();
        if (resenas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable Long id) {
        Resena resena = resenaService.getResenaById(id);
        if (resena != null) {
            return new ResponseEntity<>(resena, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Resena> getResenaByRating(@PathVariable int rating) {
        Resena resena = resenaService.getResenaByRating(rating);
        if (resena != null) {
            return new ResponseEntity<>(resena, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<Resena> createResena(@PathVariable Long clienteId, @RequestBody Resena resena) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        resena.setCliente(cliente);
        Resena createdResena = resenaService.saveResena(resena);
        return new ResponseEntity<>(createdResena, HttpStatus.CREATED);
    }

    @PatchMapping("/{clienteId}/{id}")
    public ResponseEntity<Resena> updateResena(@PathVariable Long clienteId, @PathVariable Long id, @RequestBody Resena resena) {
        Resena existingResena = resenaService.getResenaById(id);
        if (existingResena != null) {
            existingResena.setComentario(resena.getComentario());
            existingResena.setRating(resena.getRating());
            Cliente cliente = clienteService.getClienteById(clienteId);
            if (cliente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            existingResena.setCliente(cliente);
            Resena updatedResena = resenaService.saveResena(existingResena);
            return new ResponseEntity<>(updatedResena, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id) {
        Resena existingResena = resenaService.getResenaById(id);
        if (existingResena != null) {
            resenaService.deleteResena(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
