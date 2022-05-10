package com.vialt.mangastore.controllers;


import com.vialt.mangastore.entities.Purchase;
import com.vialt.mangastore.entities.User;
import com.vialt.mangastore.services.PurchasingService;
import com.vialt.mangastore.support.ResponseMessage;
import com.vialt.mangastore.support.exceptions.DateWrongRangeException;
import com.vialt.mangastore.support.exceptions.QuantityProductUnavailableException;
import com.vialt.mangastore.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/purchases")
public class PurchasingController {
    @Autowired
    private PurchasingService purchasingService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity create(@RequestBody @Valid Purchase purchase) {
        try {
            return new ResponseEntity<>(purchasingService.addPurchase(purchase), HttpStatus.OK);
        } catch (QuantityProductUnavailableException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product quantity unavailable!", e);
        }
    }



    @GetMapping("/{user}")
    public List<Purchase> getPurchases(@RequestBody @Valid User user) {
        try {
            return purchasingService.getPurchasesByUser(user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!", e);
        }
    }

    @GetMapping("/{user}/{startDate}/{endDate}")
    public ResponseEntity getPurchasesInPeriod(@Valid @PathVariable("user") User user, @PathVariable("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date start, @PathVariable("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        try {
            List<Purchase> result = purchasingService.getPurchasesByUserInPeriod(user, start, end);
            if ( result.size() <= 0 ) {
                return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found XXX!", e);
        } catch (DateWrongRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be previous end date XXX!", e);
        }
    }
    @GetMapping("/all")
    public List<Purchase> getAll(){
        return purchasingService.getAllPurchases();
    }
    @GetMapping("/{purchaseId}")
    public Purchase getPurchase(@Valid @PathVariable("purchaseId") Long purchaseId){
        return purchasingService.getPurchase(purchaseId);
    }



}
