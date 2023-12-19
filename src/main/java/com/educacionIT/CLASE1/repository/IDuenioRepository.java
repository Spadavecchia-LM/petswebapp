package com.educacionIT.CLASE1.repository;

import com.educacionIT.CLASE1.model.Duenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDuenioRepository extends JpaRepository<Duenio, Long> {

    //TODO: Implementar query methods
}
