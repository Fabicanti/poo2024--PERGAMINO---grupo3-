package com.grupo3.TP_poo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo3.TP_poo.Entitis.Country;
import com.grupo3.TP_poo.Repositories.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country addCountry(Country country){
        Country newCountry = new Country();
        newCountry.setId(country.getId());
        newCountry.setName(country.getName());

        newCountry = countryRepository.save(country);
        return newCountry;
    }

    public boolean modifyCountry(Country country){
        return true;
    }

}
