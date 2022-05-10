
package com.vialt.mangastore.controllers;


import com.vialt.mangastore.entities.Book;
import com.vialt.mangastore.entities.ProductInPurchase;
import com.vialt.mangastore.entities.Purchase;
import com.vialt.mangastore.services.ProductInPurchaseService;
import com.vialt.mangastore.services.PurchasingService;
import com.vialt.mangastore.support.ResponseMessage;
import com.vialt.mangastore.support.exceptions.QuantityProductUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/productInPurchase")
public class ProductInPurchaseController {
    @Autowired
    private ProductInPurchaseService productInPurchaseService;

    @GetMapping("/all")
    public List<ProductInPurchase> getAll(){
        return productInPurchaseService.getAllPip();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity add(@RequestBody @Valid ProductInPurchase pip) {
        return new ResponseEntity<>(productInPurchaseService.addPip(pip), HttpStatus.OK);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void remove(@RequestBody @Valid Long id) {
        productInPurchaseService.removePip(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductInPurchase(@PathVariable ("id") Long id){
        ProductInPurchase pip = productInPurchaseService.getPipById(id);
        return new ResponseEntity<>(pip, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateBook(Book book, ProductInPurchase pip){
        return new ResponseEntity<>(productInPurchaseService.updateBook(book,pip), HttpStatus.OK);
    }

}



