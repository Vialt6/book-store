package com.vialt.mangastore.services;

import com.vialt.mangastore.entities.Book;
import com.vialt.mangastore.repositories.BookRepository;
import com.vialt.mangastore.support.exceptions.BookAlreadyExistException;
import com.vialt.mangastore.support.exceptions.BooksNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = false)
    public void addBook(Book book) throws BookAlreadyExistException {
        if ( bookRepository.findById(book.getId()) != null) {
            throw new BookAlreadyExistException();
        }
        bookRepository.save(book);
    }
    
    @Transactional(readOnly = true)
    public List<Book> getAllBooks(){
        return bookRepository.findAll(); 
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooksPaginated(int pageNumber, int pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Book> pageResult = bookRepository.findAll(paging);
        if(pageResult.hasContent()){
            return pageResult.getContent();
        }
        else{
            return new ArrayList<>();
        }
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByName(String name) {
        return bookRepository.findByNameContaining(name);
    }

    @Transactional(readOnly = true)
    public List<Book> getByCategoryId(Long id){ return bookRepository.findByCategoryId(id); }

    @Transactional
    public void deleteBook(Book book) throws BooksNotFoundException {
        if ( bookRepository.findById(book.getId()) == null) {
            throw new BooksNotFoundException();
        }
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public List<Book> getBookById(Long id){ return bookRepository.findBookById(id); }
}
