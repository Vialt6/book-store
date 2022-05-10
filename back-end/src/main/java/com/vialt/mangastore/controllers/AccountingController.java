package com.vialt.mangastore.controllers;

import com.vialt.mangastore.entities.User;
import com.vialt.mangastore.services.AccountingService;
import com.vialt.mangastore.support.ResponseMessage;
import com.vialt.mangastore.support.authentication.Utils;
import com.vialt.mangastore.support.exceptions.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;


@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/users")
public class AccountingController {
    @Autowired
    private AccountingService accountingService;

    @PostMapping
    public ResponseEntity create(@RequestParam @Valid User user) {
        try {
            User added = accountingService.registerUser(user);
            return new ResponseEntity(added, HttpStatus.OK);
        } catch (MailUserAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage("Mail user already exists!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return accountingService.getAllUsers();
    }
    @GetMapping("/name")
    public ResponseEntity getCurrentUserName() {
        return new ResponseEntity(Utils.getName(), HttpStatus.OK);
    }
    @GetMapping("/email")
    public ResponseEntity<String> getCurrentUserEmail(){
        return new ResponseEntity( Utils.getEmail(), HttpStatus.OK);
    }



/*
    public Optional<String> getCurrentUserLogin() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {

                    if (authentication.getPrincipal() instanceof KeycloakPrincipal) {
                        KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
                        return principal.getName();
                    }
                    return null;
                });
    }


    KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
    KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
    KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
    AccessToken accessToken = session.getToken();
    username = accessToken.getPreferredUsername();
    emailID = accessToken.getEmail();
    lastname = accessToken.getFamilyName();
    firstname = accessToken.getGivenName();
    realmName = accessToken.getIssuer();
    Access realmAccess = accessToken.getRealmAccess();
    roles = realmAccess.getRoles();

 */

}
