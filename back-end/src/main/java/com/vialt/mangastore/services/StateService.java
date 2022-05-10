package com.vialt.mangastore.services;

import com.vialt.mangastore.entities.State;
import com.vialt.mangastore.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    @Transactional(readOnly = true)
    public List<State> getStates(){
        return stateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<State> findByCountryCode(String code){
        return stateRepository.findByCountryCode(code);
    }



}
