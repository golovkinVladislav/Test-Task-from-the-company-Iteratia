package com.vladislavgolovkin.testtaskfromthecompanyiteratia.service;



import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.ConvectHistory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


//Класс с фильтрацией историй конвертации по названию валют
public class ConvectHistorySpecs {
    public static Specification<ConvectHistory> searchByCurrencyFrom(String currencyFrom) {
        return new Specification<ConvectHistory>() {
            @Override
            public Predicate toPredicate(Root<ConvectHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("convectFrom"), "%" + currencyFrom + "%");
            }
        };
    }
    public static Specification<ConvectHistory> searchByCurrencyTo(String currencyTo) {
        return new Specification<ConvectHistory>() {
            @Override
            public Predicate toPredicate(Root<ConvectHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("convectTo"), "%" + currencyTo + "%");
            }
        };
    }
}
