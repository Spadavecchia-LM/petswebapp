package com.educacionIT.CLASE1.service;

import com.educacionIT.CLASE1.model.Duenio;
import com.educacionIT.CLASE1.model.Mascota;
import com.educacionIT.CLASE1.repository.IDuenioRepository;
import com.educacionIT.CLASE1.repository.IMascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    @Autowired
    private IDuenioRepository duenioRepository;
    @Autowired
    private IMascotaRepository mascotaRepository;

    public Mascota buscarMascota(Long id){
        return mascotaRepository.findById(id).orElseThrow(() -> new RuntimeException("no se encontro a la mascota"));
    }

    public List<Mascota> listarMascotas(){

        return mascotaRepository.findAllByOrderByNombreAsc();
    }
    public void guardarMascota(Mascota mascota, Long idDuenio){
        Duenio duenio = duenioRepository.findById(idDuenio).orElseThrow(() -> new RuntimeException("no existe un duenio con id: " + idDuenio));

        mascota.setDuenio(duenio);

        mascotaRepository.save(mascota);
    }

    public void eliminarMascota(Long id){
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(id);

        if(mascotaOptional.isPresent()){
            Mascota mascotaExistente = mascotaOptional.get();

            mascotaRepository.delete(mascotaExistente);
        }
        else {
           throw new RuntimeException("mascota no encontrada!");
        }

    }

    public void actualizarMascota(Long id, Mascota mascotaActualizada){
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(id);

        if(mascotaOptional.isPresent()){
          Mascota mascotaExistente = mascotaOptional.get();

          mascotaExistente.setDuenio(mascotaActualizada.getDuenio());
          mascotaExistente.setNombre(mascotaActualizada.getNombre());
          mascotaExistente.setEdad(mascotaActualizada.getEdad());
          mascotaExistente.setEspecie(mascotaActualizada.getEspecie());

          mascotaRepository.save(mascotaExistente);

        }
        else {
            throw new RuntimeException("mascota no encontrada!, no se pudo actualizar");
        }

    }
}
