package com.educacionIT.CLASE1.controller;

import com.educacionIT.CLASE1.model.Duenio;
import com.educacionIT.CLASE1.service.DuenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class DuenioController {

    @Autowired
    private DuenioService duenioService;

    @GetMapping("/duenios")
    public String listarDuenios(Model model){
        List<Duenio> duenios = duenioService.listarDuenios();

        model.addAttribute("duenios", duenios);

        return "duenios";
    }
    @GetMapping("/agregarDuenio")
    public String mostrarFormularioAgregarDuenio(Model model){
        model.addAttribute("duenio", new Duenio());

        return "agregarDuenio";
    }
    @PostMapping("/guardarDuenio")
    public String guardarDuenio(Duenio duenio){
        duenioService.guardarDuenio(duenio);
        return "redirect:/duenios";
    }

}
