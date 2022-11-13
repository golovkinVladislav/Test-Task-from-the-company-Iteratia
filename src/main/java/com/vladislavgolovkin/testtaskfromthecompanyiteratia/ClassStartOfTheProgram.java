package com.vladislavgolovkin.testtaskfromthecompanyiteratia;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.Currency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.CurrentDateCurrency;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.exception_handling.XMLParsingException;
import com.vladislavgolovkin.testtaskfromthecompanyiteratia.service.CurrentDateCurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import java.util.Date;
import java.util.List;
/*
Класс парсер, при запуске приложения получаем список актуальных валют и их курсов
 */
@Slf4j
public class ClassStartOfTheProgram {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static LocalDate localDate;
    @Autowired
    private CurrentDateCurrencyService currentDateCurrencyService;
    @Autowired
    private Environment environment;

    //Метод срабатывающий во время запуска приложения
    public void runAfterObjectCreated() {
        Currency currency = null;
        CurrentDateCurrency currentDateCurrency;
        try {
            // Получаем объект Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(environment.getProperty("url.cbr.ru"));
            doc.getDocumentElement().normalize();
            // Находим узел в дереве
            NodeList nList = doc.getElementsByTagName("ValCurs");
            //В цикле смотрим все дочерние узлы
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    localDate = LocalDate.parse((elem.getAttribute("Date")), FORMATTER);
                }
            }
            //Обращаемся в БД за листом всех дат, если не находим актуальную дату с сайта заходим в цикл
            List<Date> currentDateCurrencyDate = currentDateCurrencyService.getCurrentDateCurrency();
            if (currentDateCurrencyDate.stream().filter(c -> c.toString().equals(localDate.toString())).findAny().isEmpty()) {
                currentDateCurrency = new CurrentDateCurrency(localDate);
                // Находим узел в дереве
                NodeList nodeList = doc.getElementsByTagName("Valute");
                List<Currency> currenciesList = new ArrayList<>();
                //В цикле получаем все атрибуты узла и формируем и сохраняем entity в БД
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
                log.info("Data for {} is already in the database",localDate);
        } catch (Exception e) {
            log.error("Error when parsing the page");
            throw new XMLParsingException("Error when parsing the page");
        }
    }
}
