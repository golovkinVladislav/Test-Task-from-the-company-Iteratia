package com.vladislavgolovkin.testtaskfromthecompanyiteratia.service;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.Currency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.CurrentDateCurrency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.exception_handling.XMLParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//Получение актуального курса на текущую дату
@Service
@Slf4j
public class RequestForTheCurrentDate {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static LocalDate date;
    @Autowired
    private Environment environment;
    @Autowired
    private CurrentDateCurrencyService currentDateCurrencyService;

    public boolean getTheCurrentCourseForTheCurrentDate(){
        Currency currency= null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(environment.getProperty("url.cbr.ru"));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ValCurs");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;
                        date = LocalDate.parse((elem.getAttribute("Date")), FORMATTER);
                    }
                }
            if(!date.toString().equals(currentDateCurrencyService.getTheLatestCurrentDate().toString())){
                CurrentDateCurrency currentDateCurrency = new CurrentDateCurrency(date);
                NodeList nodeList = doc.getElementsByTagName("Valute");
                List<Currency> currenciesList = new ArrayList<>();
                for (int j = 0; j < nodeList.getLength(); j++) {

                    Node node = nodeList.item(j);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;
                        currency = new Currency(
                                elem.getAttribute("ID"),
                                elem.getElementsByTagName("CharCode").item(0).getTextContent(),
                                Integer.valueOf(elem.getElementsByTagName("Nominal").item(0).getTextContent()),
                                elem.getElementsByTagName("Name").item(0).getTextContent(),
                                new BigDecimal(elem.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".")),
                                currentDateCurrency);
                        currenciesList.add(currency);
                    }
                }
                currentDateCurrency.setCurrencyList(currenciesList);
                currentDateCurrencyService.saveCurrentDateCurrency(currentDateCurrency);
                log.info("Successful page parsing and entity saving currency");
            }
            log.info("Data for {} is already in the database",date);
            return true;
        } catch (Exception e){
            log.error("Error when parsing the page");
            throw new XMLParsingException("Error when parsing the page");
        }
    }
}
