package com.ecomarket.usuarios_nuevo.repository;

import com.ecomarket.usuarios_nuevo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Podemos agregar métodos personalizados para consultas específicas aquí si es necesario
}
