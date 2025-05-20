package com.ecomarket.inventario.controller;

import com.ecomarket.inventario.model.Proveedor;
import com.ecomarket.inventario.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;


    @GetMapping
    public List<Proveedor> getAllProveedores() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    public Proveedor getProveedorById(@PathVariable Long id) {
        return proveedorService.findById(id);
    }

    @PostMapping
    public Proveedor createProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.save(proveedor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Proveedor> partialUpdateCliente(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Proveedor existingProveedor = proveedorService.findById(id);
        if (existingProveedor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (proveedor.getNombre() != null) {
            existingProveedor.setNombre(proveedor.getNombre());
        }
        if (proveedor.getTelefono() != null) {
            existingProveedor.setTelefono(proveedor.getTelefono());
        }
        if (proveedor.getEmail() != null) {
            existingProveedor.setEmail(proveedor.getEmail());
        }
        if (proveedor.getDireccion() != null) {
            existingProveedor.setDireccion(proveedor.getDireccion());
        }
        Proveedor updatedProveedor = proveedorService.save(existingProveedor);
        return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Proveedor updateProveedorFull(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Proveedor existingProveedor = proveedorService.findById(id);
        if (existingProveedor != null) {
            existingProveedor.setNombre(proveedor.getNombre());
            existingProveedor.setTelefono(proveedor.getTelefono());
            existingProveedor.setEmail(proveedor.getEmail());
            return proveedorService.save(existingProveedor);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProveedor(@PathVariable Long id) {
        proveedorService.deleteById(id);
    }
}