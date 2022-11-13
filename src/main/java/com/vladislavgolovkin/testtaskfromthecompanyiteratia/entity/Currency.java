package com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "currencies",schema = "iteratia")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "valute_id")
    private String valuteId;
    @Column(name = "code")
    private String code;
    @Column(name = "nominal")
    private Integer nominal;
    @Column(name = "name")
    private String name;
    @Column(name = "value_current_course")
    private BigDecimal valueCurrentCourse;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_date_id")
    private CurrentDateCurrency currency;

    public Currency(String valuteId, String code, Integer nominal, String name, BigDecimal valueCurrentCourse, CurrentDateCurrency currency) {
        this.valuteId = valuteId;
        this.code = code;
        this.nominal = nominal;
        this.name = name;
        this.valueCurrentCourse = valueCurrentCourse;
        this.currency = currency;
    }
}
