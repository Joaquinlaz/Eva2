package com.ecomarket.reportes.Controller;

import com.ecomarket.reportes.Model.Reporte;
import com.ecomarket.reportes.Service.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReporteService reporteService;

    @Test
    public void testObtenerReportePorTipo() throws Exception {
        Reporte reporte = Reporte.builder()
                .id(1L)
                .tipo("ventas")
                .contenido("Reporte de ventas")
                .fecha(LocalDateTime.now())
                .build();

        when(reporteService.generarReporte("ventas")).thenReturn(reporte);

        mockMvc.perform(get("/api/reportes/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo", is("ventas")))
                .andExpect(jsonPath("$.contenido", is("Reporte de ventas")));
    }

    @Test
    public void testEstadoSistema() throws Exception {
        when(reporteService.verificarEstadoSistema()).thenReturn("OK");

        mockMvc.perform(get("/api/reportes/estado"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    public void testListarReportes() throws Exception {
        Reporte r1 = Reporte.builder()
                .id(1L)
                .tipo("ventas")
                .contenido("Reporte de ventas")
                .fecha(LocalDateTime.now())
                .build();

        Reporte r2 = Reporte.builder()
                .id(2L)
                .tipo("stock")
                .contenido("Reporte de stock")
                .fecha(LocalDateTime.now())
                .build();

        List<Reporte> reportes = Arrays.asList(r1, r2);
        when(reporteService.obtenerTodosLosReportes()).thenReturn(reportes);

        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].tipo", is("ventas")))
                .andExpect(jsonPath("$[1].tipo", is("stock")));
    }
}

