package com.vialt.mangastore.services;

import com.vialt.mangastore.entities.Country;
import com.vialt.mangastore.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<Country> getCountries(){
        return countryRepository.findAll();
    }
}
