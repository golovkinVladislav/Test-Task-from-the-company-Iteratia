package com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "currencies_as_of_the_current_date",schema = "iteratia")
@Getter
@Setter
@NoArgsConstructor
public class CurrentDateCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "val_curs_date")
    private LocalDate localDate;
    @OneToMany(mappedBy = "currency",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Currency> currencyList;

    public CurrentDateCurrency(LocalDate localDate) {
        this.localDate = localDate;
    }
}
