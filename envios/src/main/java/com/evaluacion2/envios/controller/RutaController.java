package com.evaluacion2.envios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evaluacion2.envios.model.Ruta;
import com.evaluacion2.envios.service.RutaService;
import com.evaluacion2.envios.service.EnviosService;
import com.evaluacion2.envios.model.Envios;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {
    @Autowired
    private RutaService rutaService;
    @Autowired
    private EnviosService enviosService;

    @PostMapping("/{envioId}")
    public ResponseEntity<Ruta> createRuta(@PathVariable Long envioId, @RequestBody Ruta ruta) {
        Envios envio = enviosService.getEnvioById(envioId);
        if (envio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ruta.setEnvio(envio);
        Ruta createdRuta = rutaService.saveRuta(ruta);
        return new ResponseEntity<>(createdRuta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ruta> getRutaById(@PathVariable Long id) {
        Ruta ruta = rutaService.getRutaById(id);
        if (ruta != null) {
            return new ResponseEntity<>(ruta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<Ruta>> getRutasByCiudad(@PathVariable String comuna) {
        List<Ruta> rutas = rutaService.findByComunaOrigen(comuna);
        if (rutas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Ruta>> getAllRutas() {
        List<Ruta> rutas = rutaService.getAllRutas();
        if (rutas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ruta> updateRuta(@PathVariable Long id, @RequestBody Ruta ruta) {
        Ruta existingRuta = rutaService.getRutaById(id);
        if (existingRuta != null) {
            if (ruta.getNombreRuta() != null) {
                existingRuta.setNombreRuta(ruta.getNombreRuta());
            }
            if (ruta.getDireccionDestino() != null) {
                existingRuta.setDireccionDestino(ruta.getDireccionDestino());
            }
            if (ruta.getComunaOrigen() != null) {
                existingRuta.setComunaOrigen(ruta.getComunaOrigen());
            }
            if (ruta.getComunaDestino() != null) {
                existingRuta.setComunaDestino(ruta.getComunaDestino());
            }
            if (ruta.getEnvio() != null) {
                existingRuta.setEnvio(ruta.getEnvio());
            }
            Ruta updatedRuta = rutaService.saveRuta(existingRuta);
            return new ResponseEntity<>(updatedRuta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        rutaService.deleteRuta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ruta> partialUpdateRuta(@PathVariable Long id, @RequestBody Ruta ruta) {
        Ruta existingRuta = rutaService.getRutaById(id);
        if (existingRuta != null) {
            existingRuta.setNombreRuta(ruta.getNombreRuta());
            existingRuta.setDireccionDestino(ruta.getDireccionDestino());
            existingRuta.setComunaOrigen(ruta.getComunaOrigen());
            existingRuta.setComunaDestino(ruta.getComunaDestino());
            existingRuta.setEnvio(ruta.getEnvio());
            Ruta updatedRuta = rutaService.saveRuta(existingRuta);
            return new ResponseEntity<>(updatedRuta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
