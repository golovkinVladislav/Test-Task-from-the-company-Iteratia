package com.vladislavgolovkin.testtaskfromthecompanyiteratia.service;


import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.Currency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrency(){
        return currencyRepository.getAllByCurrency();
    }
}
