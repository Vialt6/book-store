package com.vialt.mangastore.services;


import com.vialt.mangastore.entities.Book;
import com.vialt.mangastore.entities.ProductInPurchase;
import com.vialt.mangastore.entities.Purchase;
import com.vialt.mangastore.entities.User;
import com.vialt.mangastore.repositories.BookRepository;
import com.vialt.mangastore.repositories.ProductInPurchaseRepository;
import com.vialt.mangastore.repositories.PurchaseRepository;
import com.vialt.mangastore.repositories.UserRepository;
import com.vialt.mangastore.support.authentication.Utils;
import com.vialt.mangastore.support.exceptions.DateWrongRangeException;
import com.vialt.mangastore.support.exceptions.QuantityProductUnavailableException;
import com.vialt.mangastore.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


@Service("PurchasingService")
public class PurchasingService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProductInPurchaseService productInPurchaseService;


    @Transactional(readOnly = false)
    public Purchase addPurchase(Purchase purchase) throws QuantityProductUnavailableException {
        purchase.setBuyer(Utils.getEmail());
        Purchase result = purchaseRepository.save(purchase);
        List<ProductInPurchase> list = productInPurchaseRepository.findProductInPurchaseByPurchase(purchase);
        for( ProductInPurchase pip : list) {
            pip.setPurchase(purchase);
            ProductInPurchase justAdded = productInPurchaseRepository.save(pip);
            Book book = bookRepository.getById(justAdded.getBookId());
            int newQuantity = book.getUnitsInStock() - pip.getQuantity();
            if ( newQuantity < 0 ) {
                throw new QuantityProductUnavailableException();
            }
            //if(pip.getUnitPrice() != book.getUnitPrice())
            book.setUnitsInStock(newQuantity);

            entityManager.refresh(pip);
        }
        entityManager.refresh(result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByUser(User user) throws UserNotFoundException {
        if ( !userRepository.existsById(user.getId()) ) {
            throw new UserNotFoundException();
        }
        return purchaseRepository.findByBuyer(user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> getPurchasesByUserInPeriod(User user, Date startDate, Date endDate) throws    UserNotFoundException, DateWrongRangeException {
        if ( !userRepository.existsById(user.getId()) ) {
            throw new UserNotFoundException();
        }
        if ( startDate.compareTo(endDate) >= 0 ) {
            throw new DateWrongRangeException();
        }
        return purchaseRepository.findByBuyerInPeriod(startDate, endDate, user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Purchase getPurchase(Long purchaseId) {
        return purchaseRepository.getById(purchaseId);
    }


}
