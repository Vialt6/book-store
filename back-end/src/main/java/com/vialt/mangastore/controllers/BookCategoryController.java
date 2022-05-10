package com.vialt.mangastore.controllers;

import com.vialt.mangastore.entities.BookCategory;
import com.vialt.mangastore.services.BookCategoryService;
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
@RequestMapping("/book-category")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    @GetMapping
    public ResponseEntity getAllCategories(){
        List<BookCategory> result = bookCategoryService.getAllBookCategories();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
