package com.grupo3.TP_poo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo3.TP_poo.Entitis.Country;

@Repository
public interface CountryRepository  extends JpaRepository<Country, Long>{
    
}
