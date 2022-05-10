package com.vialt.mangastore.repositories;



import com.vialt.mangastore.entities.Purchase;
import com.vialt.mangastore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository("PurchaseRepository")
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByBuyer(User user);
    List<Purchase> findByPurchaseTime(Date date);

    @Query("select p from Purchase p where p.purchaseTime > ?1 and p.purchaseTime < ?2 and p.buyer = ?3")
    List<Purchase> findByBuyerInPeriod(Date startDate, Date endDate, User user);

}
