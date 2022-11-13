package com.vladislavgolovkin.testtaskfromthecompanyiteratia.service;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.ConvectHistory;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.exception_handling.NoSuchCurrentlyException;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.repository.ConvectHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;



@Service
public class ConvectHistoryService {
    @Autowired
    private ConvectHistoryRepository convectRepository;
    @Autowired
    private CurrentDateCurrencyService currentDateCurrencyService;

    //Сохранение истории в БД, формирование объекта через билдер для простоты
    public ConvectHistory saveHistoryConvect(String currencyOne,String currencyTwo,String value,String result){
        ConvectHistory convectHistory = ConvectHistory.builder()
                .convectFrom(currencyOne.substring(0, currencyOne.indexOf("|") - 7))
                .convectTo(currencyTwo.substring(0, currencyTwo.indexOf("|") - 7))
                .value(value)
                .result(result.replace(",", "."))
                .courseFrom(currencyOne.substring(currencyOne.indexOf("|") + 1))
                .courseTo(currencyTwo.substring(currencyTwo.indexOf("|") + 1))
                .localDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDateCurrencyService.getTheLatestCurrentDate())))
                .build();
        return convectRepository.save(convectHistory);

    }
    // Средний курс конвертации за неделю
    public BigDecimal getAverageConversionRate(LocalDate after, LocalDate before, String one, String two){
        BigDecimal averageConversionRate = convectRepository.getAverageConversionRate(after, before, one, two);
        if(averageConversionRate != null){
            return averageConversionRate;
        }
        throw new NoSuchCurrentlyException("No such currency pair was found");
    }
    //Суммарный объём конвертаций за неделю
    public BigDecimal getTotalVolumeOfConversions(LocalDate after, LocalDate before, String one, String two){
        BigDecimal totalVolumeOfConversions = convectRepository.getTotalVolumeOfConversions(after, before, one, two);
        if( totalVolumeOfConversions != null){
            return totalVolumeOfConversions;
        }
        throw new NoSuchCurrentlyException("No such currency pair was found");
    }


    public Page<ConvectHistory> getConvectWithPagingAndFiltering(Integer page, String currencyFrom, String currencyTo) {
        Specification<ConvectHistory> filter = Specification.where(null);
        if(page == null){
            page = 1;
        }
        if(currencyFrom != null){
            filter = filter.and(ConvectHistorySpecs.searchByCurrencyFrom(currencyFrom));
        }
        if(currencyTo != null){
            filter = filter.and(ConvectHistorySpecs.searchByCurrencyTo(currencyTo));
        }
        return convectRepository.findAll(filter, PageRequest.of(page-1,Integer.MAX_VALUE));
    }

}
