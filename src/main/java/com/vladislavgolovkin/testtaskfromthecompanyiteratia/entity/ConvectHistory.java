package com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "convects",schema = "iteratia")
@Getter
@Setter
public class ConvectHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "convect_from")
    private String convectFrom;
    @Column(name = "convect_to")
    private String convectTo;
    @Column(name = "value")
    private String value;
    @Column(name = "result")
    private String result;
    @Column(name = "course_from")
    private String courseFrom;
    @Column(name = "course_to")
    private String courseTo;
    @Column(name = "val_curs_date")
    private LocalDate localDate;

    ConvectHistory(String convectFrom, String convectTo, String value, String result, String courseFrom, String courseTo, LocalDate localDate) {
        this.convectFrom = convectFrom;
        this.convectTo = convectTo;
        this.value = value;
        this.result = result;
        this.courseFrom = courseFrom;
        this.courseTo = courseTo;
        this.localDate = localDate;
    }

    public ConvectHistory() {
    }

    public static ConvectHistoryBuilder builder() {
        return new ConvectHistoryBuilder();
    }


    public static class ConvectHistoryBuilder {
        private String convectFrom;
        private String convectTo;
        private String value;
        private String result;
        private String courseFrom;
        private String courseTo;
        private LocalDate localDate;

        ConvectHistoryBuilder() {
        }


        public ConvectHistoryBuilder convectFrom(String convectFrom) {
            this.convectFrom = convectFrom;
            return this;
        }

        public ConvectHistoryBuilder convectTo(String convectTo) {
            this.convectTo = convectTo;
            return this;
        }

        public ConvectHistoryBuilder value(String value) {
            this.value = value;
            return this;
        }

        public ConvectHistoryBuilder result(String result) {
            this.result = result;
            return this;
        }

        public ConvectHistoryBuilder courseFrom(String courseFrom) {
            this.courseFrom = courseFrom;
            return this;
        }

        public ConvectHistoryBuilder courseTo(String courseTo) {
            this.courseTo = courseTo;
            return this;
        }

        public ConvectHistoryBuilder localDate(LocalDate localDate) {
            this.localDate = localDate;
            return this;
        }

        public ConvectHistory build() {
            return new ConvectHistory(convectFrom, convectTo, value, result, courseFrom, courseTo, localDate);
        }

        public String toString() {
            return "ConvectHistory.ConvectHistoryBuilder(convectFrom=" + this.convectFrom + ", convectTo=" + this.convectTo + ", value=" + this.value + ", result=" + this.result + ", courseFrom=" + this.courseFrom + ", courseTo=" + this.courseTo + ", localDate=" + this.localDate + ")";
        }
    }
}
