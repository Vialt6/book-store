package com.vialt.mangastore.controllers;

import com.vialt.mangastore.entities.Book;
import com.vialt.mangastore.services.BookService;
import com.vialt.mangastore.support.ResponseMessage;
import com.vialt.mangastore.support.exceptions.BookAlreadyExistException;
import com.vialt.mangastore.support.exceptions.BooksNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity create(@RequestParam Book book){
        try{
            bookService.addBook(book);
        }catch (BookAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseMessage("Book already exist!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Added successful!"), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getAllBooks(){
        List<Book> result =  bookService.getAllBooks();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity getAllBooksPaginated(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy ){
        List<Book> result = bookService.getAllBooksPaginated(pageNumber,pageSize,sortBy);
        if(result.size()<=0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search/searchbykeyword")
    public ResponseEntity getByName(@RequestParam(required = false) String name){
        List<Book> result = bookService.getBooksByName(name);
        if(result.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("search/categoryid")
    public ResponseEntity getByCategoryId(@RequestParam(required = false) Long id){
        List<Book> result = bookService.getByCategoryId(id);
        if(result.size() <= 0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteBook(@RequestParam Book book){
        try{
            bookService.deleteBook(book);
        } catch (BooksNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Book not found!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Deleted successful!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable ("id") Long id){
        List<Book> book = bookService.getBookById(id);
        if(book.size()<=0){
            return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
