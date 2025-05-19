package com.evaluacion2.envios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evaluacion2.envios.model.Envios;
import com.evaluacion2.envios.service.EnviosService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnviosController {
    @Autowired
    private EnviosService enviosService;

    @PostMapping
    public ResponseEntity<Envios> createEnvio(@RequestBody Envios envio) {
        Envios createdEnvio = enviosService.saveEnvio(envio);
        return new ResponseEntity<>(createdEnvio, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envios> getEnvioById(@PathVariable Long id) {
        Envios envio = enviosService.getEnvioById(id);
        if (envio != null) {
            return new ResponseEntity<>(envio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Envios>> getAllEnvios() {
        List<Envios> envios = enviosService.getAllEnvios();
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Envios>> getEnviosByFecha(@PathVariable Date fecha) {
        List<Envios> envios = enviosService.findByFecha(fecha);
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envios>> getEnviosByEstado(@PathVariable String estado) {
        List<Envios> envios = enviosService.findByEstado(estado);
        if (envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envios> updateEnvio(@PathVariable Long id, @RequestBody Envios envio) {
        Envios existingEnvio = enviosService.getEnvioById(id);
        if (existingEnvio != null) {
            existingEnvio.setFechaEnvio(envio.getFechaEnvio());
            existingEnvio.setEstadoEnvio(envio.getEstadoEnvio());
            existingEnvio.setAddress(envio.getAddress());
            existingEnvio.setCiudad(envio.getCiudad());
            existingEnvio.setNombre(envio.getNombre());
            existingEnvio.setClienteId(envio.getClienteId());
            Envios updatedEnvio = enviosService.saveEnvio(existingEnvio);
            return new ResponseEntity<>(updatedEnvio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        enviosService.deleteEnvio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Envios> patchEnvio(@PathVariable Long id, @RequestBody Envios envio) {
        Envios existingEnvio = enviosService.getEnvioById(id);
        if (existingEnvio != null) {
            if (envio.getFechaEnvio() != null) {
                existingEnvio.setFechaEnvio(envio.getFechaEnvio());
            }
            if (envio.getEstadoEnvio() != null) {
                existingEnvio.setEstadoEnvio(envio.getEstadoEnvio());
            }
            if (envio.getAddress() != null) {
                existingEnvio.setAddress(envio.getAddress());
            }
            if (envio.getCiudad() != null) {
                existingEnvio.setCiudad(envio.getCiudad());
            }
            if (envio.getNombre() != null) {
                existingEnvio.setNombre(envio.getNombre());
            }
            if (envio.getClienteId() != null) {
                existingEnvio.setClienteId(envio.getClienteId());
            }
            Envios updatedEnvio = enviosService.saveEnvio(existingEnvio);
            return new ResponseEntity<>(updatedEnvio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
