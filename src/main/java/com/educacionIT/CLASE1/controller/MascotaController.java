package com.educacionIT.CLASE1.controller;

import com.educacionIT.CLASE1.model.Duenio;
import com.educacionIT.CLASE1.model.Mascota;
import com.educacionIT.CLASE1.service.DuenioService;
import com.educacionIT.CLASE1.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MascotaController {

    private DuenioService duenioService;
    private MascotaService mascotaService;

    @Autowired
    public MascotaController(DuenioService duenioService, MascotaService mascotaService) {
        this.duenioService = duenioService;
        this.mascotaService = mascotaService;
    }
    @GetMapping("/")
    public String redirectToMascotas(){
        return "redirect:/mascotas";
    }

    @GetMapping("/mascotas")
    public String listarMascotas(Model model){
            List<Mascota> mascotas =  mascotaService.listarMascotas();

            model.addAttribute("mascotas", mascotas);

            return "mascotas";
    }
    @GetMapping("/agregarMascota")
    public String mostrarFormularioAgregarMascota(Model model){
        model.addAttribute("duenios", duenioService.listarDuenios());
        model.addAttribute("mascota", new Mascota());
        return "agregarMascota";
    }
    @PostMapping("/guardarMascota")
    public String guardarMascota(@ModelAttribute  Mascota mascota,@RequestParam Long idDuenio){
        mascotaService.guardarMascota(mascota, idDuenio);
        return "redirect:/mascotas";
    }
    @GetMapping("/eliminarMascota/{id}")
    public String eliminarMascota(@PathVariable Long id){
        mascotaService.eliminarMascota(id);
        return "redirect:/mascotas";
    }
    @GetMapping("/actualizarMascota/{id}")
    public String mostrarFormularioActualizarMascota(@PathVariable Long id, Model model){
        Mascota mascota = mascotaService.buscarMascota(id);

        model.addAttribute("mascota", mascota);
        model.addAttribute("duenios", duenioService.listarDuenios());

        return "actualizarMascota";
    }
    @PostMapping("/actualizarMascota/{id}")
    public String actualizarMascota(@PathVariable Long id , @ModelAttribute Mascota mascotaActualizada, @RequestParam Long idDuenio){

        mascotaActualizada.setDuenio(duenioService.obtenerDuenio(idDuenio));

        mascotaService.actualizarMascota(id, mascotaActualizada);



        return "redirect:/mascotas";
    }
}