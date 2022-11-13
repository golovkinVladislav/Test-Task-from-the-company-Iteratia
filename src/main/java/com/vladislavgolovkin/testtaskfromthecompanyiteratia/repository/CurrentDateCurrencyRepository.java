package com.vladislavgolovkin.testtaskfromthecompanyiteratia.repository;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.CurrentDateCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CurrentDateCurrencyRepository extends JpaRepository<CurrentDateCurrency,Long> {
    @Query(value = "SELECT val_curs_date FROM currencies_as_of_the_current_date",nativeQuery = true)
    List<Date> getCurrentDateCurrency();

    @Query(value = "SELECT val_curs_date FROM iteratia.currencies_as_of_the_current_date ORDER BY id DESC LIMIT 1",nativeQuery = true)
    Date getTheLatestCurrentDate();
}
