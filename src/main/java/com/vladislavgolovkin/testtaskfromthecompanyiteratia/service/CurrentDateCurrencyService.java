package com.vladislavgolovkin.testtaskfromthecompanyiteratia.service;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.CurrentDateCurrency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.repository.CurrentDateCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class CurrentDateCurrencyService {
    @Autowired
    private CurrentDateCurrencyRepository currentDateCurrencyRepository;

    public CurrentDateCurrency saveCurrentDateCurrency(CurrentDateCurrency currentDateCurrency){
        return currentDateCurrencyRepository.save(currentDateCurrency);
    }

    public List<Date> getCurrentDateCurrency(){
        return currentDateCurrencyRepository.getCurrentDateCurrency();
    }

    public Date getTheLatestCurrentDate(){
        return currentDateCurrencyRepository.getTheLatestCurrentDate();
    }
}
