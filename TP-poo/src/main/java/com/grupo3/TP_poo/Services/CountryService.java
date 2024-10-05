package com.grupo3.TP_poo.Services;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo3.TP_poo.Entitis.Country;
import com.grupo3.TP_poo.Repositories.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> retriveAllCountries(){
        return countryRepository.findAll();
    }

    public Country getCountry(Long id){
        return countryRepository.findById(id)
                        .orElseThrow(()-> new NoSuchElementException());
    }

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
