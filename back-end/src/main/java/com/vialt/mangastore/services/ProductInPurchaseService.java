
package com.vialt.mangastore.services;

import com.vialt.mangastore.entities.Book;
import com.vialt.mangastore.entities.ProductInPurchase;
import com.vialt.mangastore.entities.Purchase;
import com.vialt.mangastore.repositories.BookRepository;
import com.vialt.mangastore.repositories.ProductInPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ProductInPurchaseService")
public class ProductInPurchaseService {

    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<ProductInPurchase> getAllPip() {
        return productInPurchaseRepository.findAll();
    }

    @Transactional(readOnly = false)
    public ProductInPurchase addPip(ProductInPurchase pip) {
        return productInPurchaseRepository.save(pip);
    }

    @Transactional(readOnly = false)
    public void removePip(Long id){
        ProductInPurchase pip = productInPurchaseRepository.findProductInPurchaseById(id);
        productInPurchaseRepository.delete(pip);
    }

    /*
    @Transactional(readOnly = false)
    public


     */

    @Transactional(readOnly = true)
    public ProductInPurchase getPipById(Long id){
        return productInPurchaseRepository.findProductInPurchaseById(id);
    }

    @Transactional(readOnly = false)
    public ProductInPurchase update(ProductInPurchase pip){
        ProductInPurchase productInPurchase = productInPurchaseRepository.findProductInPurchaseById(pip.getId());
       // productInPurchase.setBook(pip.getBook());
        return productInPurchaseRepository.save(productInPurchase);
    }

    @Transactional(readOnly = false)
    public ProductInPurchase updateBook(Book book,ProductInPurchase pip){
        ProductInPurchase productInPurchase = productInPurchaseRepository.findProductInPurchaseById(pip.getId());
        pip.setBookId(book.getId());
        return productInPurchaseRepository.save(productInPurchase);
    }
    public ProductInPurchase updatePurchase(Purchase p, ProductInPurchase pip){
        pip.setPurchase(p);
        return productInPurchaseRepository.save(pip);
    }
}




