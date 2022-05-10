package com.vialt.mangastore.services;

import com.vialt.mangastore.entities.User;
import com.vialt.mangastore.repositories.UserRepository;
import com.vialt.mangastore.support.exceptions.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountingService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = false)
    public User registerUser(User user) throws MailUserAlreadyExistsException {
        if ( userRepository.existsByEmail(user.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
