package com.educacionIT.CLASE1.service;

import com.educacionIT.CLASE1.model.Duenio;
import com.educacionIT.CLASE1.repository.IDuenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuenioService {

    @Autowired
    private IDuenioRepository duenioRepository;

    public Duenio obtenerDuenio(Long id){
        return duenioRepository.findById(id).orElseThrow(() -> new RuntimeException("no se encontro al duenio"));
    }

    public List<Duenio> listarDuenios(){
        return duenioRepository.findAll();
    }
    public void guardarDuenio(Duenio duenio){
        duenioRepository.save(duenio);
    }
    public void eliminarDuenio(Long id){
        duenioRepository.deleteById(id);
    }

}
