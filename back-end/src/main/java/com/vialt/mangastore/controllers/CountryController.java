package com.vialt.mangastore.controllers;

import com.vialt.mangastore.entities.Country;
import com.vialt.mangastore.services.CountryService;
import com.vialt.mangastore.support.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity getAllCountries(){
        List<Country> result = countryService.getCountries();
        if(result.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
