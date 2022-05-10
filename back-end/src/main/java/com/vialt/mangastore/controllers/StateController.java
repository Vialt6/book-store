package com.vialt.mangastore.controllers;

import com.vialt.mangastore.entities.State;
import com.vialt.mangastore.services.StateService;
import com.vialt.mangastore.support.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/states")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping
    public ResponseEntity getAllCountries(){
        List<State> result = stateService.getStates();
        if(result.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/search/findByCountryCode")
    public ResponseEntity findByCountryCode(@RequestParam String code){
        List<State> result = stateService.findByCountryCode(code);
        if(result.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
