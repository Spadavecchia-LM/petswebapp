package com.educacionIT.CLASE1.repository;

import com.educacionIT.CLASE1.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMascotaRepository extends JpaRepository<Mascota, Long> {

   Optional<Mascota> findByNombre(String nombre);

   @Query("SELECT m FROM Mascota m ORDER BY LOWER(m.nombre) ASC" )
   List<Mascota> findAllByOrderByNombreAsc();
}
