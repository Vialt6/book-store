package com.vialt.mangastore.services;

import com.vialt.mangastore.entities.BookCategory;
import com.vialt.mangastore.repositories.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryService {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    public List<BookCategory> getAllBookCategories(){return bookCategoryRepository.findAll();}

}
