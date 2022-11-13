package com.vladislavgolovkin.testtaskfromthecompanyiteratia.controller;


import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.ConvectHistory;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.Currency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.service.ConvectHistoryService;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.service.CurrencyService;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.service.RequestForTheCurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class ConverterController {
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ConvectHistoryService convectHistoryService;
    @Autowired
    private RequestForTheCurrentDate requestForTheCurrentDate;


    @GetMapping("/convector")
    public String getAllCurrency(Model model,@RequestParam(value = "currencyFrom",required = false) String currencyFrom,
                                 @RequestParam(value = "currencyTo",required = false) String currencyTo,
                                 @RequestParam(value = "page",required = false) Integer page){
        requestForTheCurrentDate.getTheCurrentCourseForTheCurrentDate();
        model.addAttribute("currencies",currencyService.getAllCurrency().stream().sorted(Comparator.comparing(Currency::getName)).collect(Collectors.toList()));
        List<ConvectHistory> content = convectHistoryService.getConvectWithPagingAndFiltering(page,currencyFrom,currencyTo).getContent();
        model.addAttribute("convecthistory",content);
        model.addAttribute("currencyFrom",currencyFrom);
        model.addAttribute("currencyTo",currencyTo);
        return "start";
    }
    @PostMapping( "/convector")
    public String resultConvector(@RequestParam("currency1") String currencyOne,
                                   @RequestParam("currency2") String currencyTwo,
                                   @RequestParam(value = "value") String value,
                                   @RequestParam(value = "result") String result){
        convectHistoryService.saveHistoryConvect(currencyOne,currencyTwo,value,result);
        return "redirect:/convector";
    }

    @GetMapping("/stat")
    public String getStatistic(@RequestParam("currency3") String currencyOne,
                       @RequestParam("currency4") String currencyTwo,
                       @RequestParam(value = "date") String date,
                       Model model) {
        BigDecimal averageConversionRate = convectHistoryService.getAverageConversionRate(LocalDate.parse(date), LocalDate.parse(date).plusWeeks(1L), currencyOne, currencyTwo);
        BigDecimal totalVolumeOfConversions = convectHistoryService.getTotalVolumeOfConversions(LocalDate.parse(date), LocalDate.parse(date).plusWeeks(1L), currencyOne, currencyTwo);
        DecimalFormat decimalFormat = new DecimalFormat("0.####");
        NumberFormat f = NumberFormat.getInstance(new Locale("ru","RU"));
        model.addAttribute("averageValue",decimalFormat.format(averageConversionRate).toString().replace(",","."));
        model.addAttribute("getSumResultValue",f.format(totalVolumeOfConversions).replace(",","."));
        model.addAttribute("dateStart",LocalDate.parse(date));
        model.addAttribute("dateEnd",LocalDate.parse(date).plusWeeks(1L));
        model.addAttribute("currencyOne",currencyOne);
        model.addAttribute("currencyTwo",currencyTwo);
        return "statistic";
    }
}


