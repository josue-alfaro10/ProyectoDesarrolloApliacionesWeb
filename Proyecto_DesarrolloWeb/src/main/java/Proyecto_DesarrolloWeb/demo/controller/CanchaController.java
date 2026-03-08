/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto_DesarrolloWeb.demo.controller;

import Proyecto_DesarrolloWeb.demo.domain.Cancha;
import Proyecto_DesarrolloWeb.demo.service.CanchaService;
import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Alfaro
 */
@Controller
@RequestMapping("/cancha")
public class CanchaController {
    
    
    private final CanchaService canchaService;

    @Autowired
    private MessageSource messageSource;

    public CanchaController(CanchaService canchaService) {
        this.canchaService = canchaService;
    }

    /**
     * Listado de canchas disponibles
     */
    @GetMapping("/listado")
    public String listado(Model model) {
        var canchas = canchaService.getCanchas(true);
        model.addAttribute("canchas", canchas);
        model.addAttribute("totalCanchas", canchas.size());
        return "/cancha/listado";
    }

    /**
     * Detalles de una cancha específica
     */
    @GetMapping("/detalles/{idCancha}")
    public String detalles(@PathVariable("idCancha") Integer idCancha, Model model, RedirectAttributes redirectAttributes) {
        Optional<Cancha> canchaOpt = canchaService.getCancha(idCancha);
        if (canchaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", 
                messageSource.getMessage("cancha.error.noexiste", null, Locale.getDefault()));
            return "redirect:/cancha/listado";
        }
        model.addAttribute("cancha", canchaOpt.get());
        return "/cancha/detalles";
    }

    /**
     * Formulario para crear una nueva cancha (solo administrador)
     */
    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("cancha", new Cancha());
        return "/cancha/crear";
    }

    /**
     * Guardar una nueva cancha (solo administrador)
     */
    @PostMapping("/guardar")
    public String guardar(@Valid Cancha cancha, RedirectAttributes redirectAttributes) {
        try {
            canchaService.save(cancha);
            redirectAttributes.addFlashAttribute("todoOk", 
                messageSource.getMessage("cancha.guardada", null, Locale.getDefault()));
            return "redirect:/cancha/listado";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                messageSource.getMessage("cancha.error.guardar", null, Locale.getDefault()));
            return "redirect:/cancha/crear";
        }
    }

    
    @GetMapping("/modificar/{idCancha}")
    public String modificar(@PathVariable("idCancha") Integer idCancha, Model model, RedirectAttributes redirectAttributes) {
        Optional<Cancha> canchaOpt = canchaService.getCancha(idCancha);
        if (canchaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", 
                messageSource.getMessage("cancha.error.noexiste", null, Locale.getDefault()));
            return "redirect:/cancha/listado";
        }
        model.addAttribute("cancha", canchaOpt.get());
        return "/cancha/modificar";
    }

    /**
     * Actualizar canchas
     */
    @PostMapping("/actualizar")
    public String actualizar(@Valid Cancha cancha, RedirectAttributes redirectAttributes) {
        try {
            canchaService.update(cancha);
            redirectAttributes.addFlashAttribute("todoOk", 
                messageSource.getMessage("cancha.actualizada", null, Locale.getDefault()));
            return "redirect:/cancha/listado";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                messageSource.getMessage("cancha.error.actualizar", null, Locale.getDefault()));
            return "redirect:/cancha/modificar/" + cancha.getIdCancha();
        }
    }

    /**
     * Eliminar una cancha
     */
    @PostMapping("/eliminar")
    public String eliminar(Integer idCancha, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "cancha.eliminada";
        try {
            canchaService.delete(idCancha);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "cancha.error.noexiste";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "cancha.error.tieneReservas";
        } catch (Exception e) {
            titulo = "error";
            detalle = "cancha.error.general";
        }
        redirectAttributes.addFlashAttribute(titulo, 
            messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/cancha/listado";
    }
}

