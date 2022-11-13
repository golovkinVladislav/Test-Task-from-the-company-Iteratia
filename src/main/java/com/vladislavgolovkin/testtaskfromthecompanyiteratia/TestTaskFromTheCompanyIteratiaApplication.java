package com.vladislavgolovkin.testtaskfromthecompanyiteratia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestTaskFromTheCompanyIteratiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskFromTheCompanyIteratiaApplication.class, args);
    }
    @Bean(initMethod = "runAfterObjectCreated")
    public ClassStartOfTheProgram getBeanInit(){
        return new ClassStartOfTheProgram();
    }

}
