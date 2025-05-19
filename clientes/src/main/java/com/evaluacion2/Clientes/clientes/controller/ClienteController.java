package com.evaluacion2.Clientes.clientes.controller;

import com.evaluacion2.Clientes.clientes.model.Cliente;
import com.evaluacion2.Clientes.clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    //crea un cliente nuevo utilizando Postman
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente createdCliente = clienteService.saveCliente(cliente);
        return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    //obtiene un cliente por su ID utilizando Postman
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<Cliente>> getClientesByUsername(@PathVariable String username) {
        List<Cliente> clientes = clienteService.findByUsername(username);
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Cliente>> getClientesByCiudad(@PathVariable String ciudad) {
        List<Cliente> clientes = clienteService.findByCiudad(ciudad);
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/fechaRegistro")
    public ResponseEntity<List<Cliente>> getClientesByFechaRegistro(@RequestParam Date startDate, @RequestParam Date endDate) {
        List<Cliente> clientes = clienteService.findByFechaRegistroBetween(startDate, endDate);
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        cliente.setRun(cliente.getRun());
        cliente.setUsername(cliente.getUsername());
        cliente.setFechaRegistro(cliente.getFechaRegistro());
        cliente.setFechaNacimiento(cliente.getFechaNacimiento());
        cliente.setCiudad(cliente.getCiudad());
        cliente.setCorreo(cliente.getCorreo());
        cliente.setAddress(cliente.getAddress());
        Cliente updatedCliente = clienteService.saveCliente(cliente);
        return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> partialUpdateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente existingCliente = clienteService.getClienteById(id);
        if (existingCliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (cliente.getUsername() != null) {
            existingCliente.setUsername(cliente.getUsername());
        }
        if (cliente.getCiudad() != null) {
            existingCliente.setCiudad(cliente.getCiudad());
        }
        if (cliente.getRun() != null) {
            existingCliente.setRun(cliente.getRun());
        }
        if (cliente.getCorreo() != null) {
            existingCliente.setCorreo(cliente.getCorreo());
        }
        if (cliente.getFechaRegistro() != null) {
            existingCliente.setFechaRegistro(cliente.getFechaRegistro());
        }
        if (cliente.getAddress() != null) {
            existingCliente.setAddress(cliente.getAddress());
        }
        if (cliente.getFechaNacimiento() != null) {
            existingCliente.setFechaNacimiento(cliente.getFechaNacimiento());
        }
        Cliente updatedCliente = clienteService.saveCliente(existingCliente);
        return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
