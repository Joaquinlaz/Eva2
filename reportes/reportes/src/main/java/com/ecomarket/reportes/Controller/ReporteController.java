package com.ecomarket.reportes.Controller;

import com.ecomarket.reportes.Model.Reporte;
import com.ecomarket.reportes.Service.ReporteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }


    @GetMapping("/{tipo}")
    public Reporte obtenerReporte(@PathVariable String tipo) {
        return service.generarReporte(tipo);
    }


    @GetMapping("/estado")
    public String estadoSistema() {
        return service.verificarEstadoSistema();
    }

 
    @GetMapping
    public List<Reporte> listarReportes() {
        return service.obtenerTodosLosReportes();
    }
}