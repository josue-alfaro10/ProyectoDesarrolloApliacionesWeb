/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_DesarrolloWeb.demo.service;

import Proyecto_DesarrolloWeb.demo.domain.Cancha;
import Proyecto_DesarrolloWeb.demo.repository.CanchaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alfaro
 */
@Service
public class CanchaService {
    
    @Autowired
    private CanchaRepository canchaRepository;

    // Listar todas las canchas
    @Transactional(readOnly = true)
    public List<Cancha> listarTodas() {
        return canchaRepository.findAll();
    }

    // Listar solo canchas activas
    @Transactional(readOnly = true)
    public List<Cancha> listarActivas() {
        return canchaRepository.findByEstadoTrue();
    }

    // Buscar por ID
    @Transactional(readOnly = true)
    public Optional<Cancha> buscarPorId(Integer id) {
        return canchaRepository.findById(id);
    }

    // Buscar por tipo
    @Transactional(readOnly = true)
    public List<Cancha> buscarPorTipo(String tipo) {
        return canchaRepository.findByTipo(tipo);
    }

    // Crear nueva cancha
    public Cancha guardar(Cancha cancha) {
        return canchaRepository.save(cancha);
    }

    // Actualizar cancha existente
    public Cancha actualizar(Integer id, Cancha canchaActualizada) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada con id: " + id));

        cancha.setNombre(canchaActualizada.getNombre());
        cancha.setUbicacion(canchaActualizada.getUbicacion());
        cancha.setTipo(canchaActualizada.getTipo());
        cancha.setPrecioHora(canchaActualizada.getPrecioHora());
        cancha.setDescripcion(canchaActualizada.getDescripcion());
        cancha.setEstado(canchaActualizada.getEstado());

        return canchaRepository.save(cancha);
    }

    // Desactivar cancha (borrado lógico)
    public void desactivar(Integer id) {
        Cancha cancha = canchaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada con id: " + id));
        cancha.setEstado(false);
        canchaRepository.save(cancha);
    }

    // Eliminar cancha (borrado físico)
    public void eliminar(Integer id) {
        if (!canchaRepository.existsById(id)) {
            throw new RuntimeException("Cancha no encontrada con id: " + id);
        }
        canchaRepository.deleteById(id);
    }
}

