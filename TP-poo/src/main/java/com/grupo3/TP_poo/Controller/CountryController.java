package com.grupo3.TP_poo.Controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.grupo3.TP_poo.Entitis.Country;
import com.grupo3.TP_poo.Services.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public List<Country> retriveAllCountries(){
        return countryService.retriveAllCountries();
    }
    
    @GetMapping("/countries/{id}")
    public Country getCountry(@PathVariable Long id){
        return countryService.getCountry(id);
    }

    @PostMapping("/countries/add")
    public Country addCountry(@RequestBody Country country) throws NoSuchAlgorithmException{
        return countryService.addCountry(country);
    }
    

}
