/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Proyecto_DesarrolloWeb.demo.repository;

import Proyecto_DesarrolloWeb.demo.domain.Cancha;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alfaro
 */
public interface CanchaRepository extends JpaRepository<Cancha, Integer> {
    // Buscar canchas que esten disponibles
    List<Cancha> findByEstadoTrue();

    // Buscra por tipo
    List<Cancha> findByTipo(String tipo);

    // buscar por el nombre de la cancha
    List<Cancha> findByNombreContainingIgnoreCase(String nombre);

    // Buscar las que estan activas
    List<Cancha> findByTipoAndEstadoTrue(String tipo);
}

