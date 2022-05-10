package com.vialt.mangastore.repositories;


import com.vialt.mangastore.entities.ProductInPurchase;
import com.vialt.mangastore.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductInPurchaseRepository")
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Long> {
    List<ProductInPurchase> findProductInPurchaseByPurchase(Purchase purchase);
    ProductInPurchase findProductInPurchaseById(Long id);

}
