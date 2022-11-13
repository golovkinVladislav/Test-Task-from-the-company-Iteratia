package com.vladislavgolovkin.testtaskfromthecompanyiteratia.repository;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long>{
    @Query("SELECT c FROM Currency c JOIN CurrentDateCurrency cr ON cr.id = c.currency and c.currency = ALL (select max(c.currency) from Currency c)")
    List<Currency> getAllByCurrency();
}
