package com.vladislavgolovkin.testtaskfromthecompanyiteratia.repository;

import com.vladislavgolovkin.testtaskfromthecompanyiteratia.entity.ConvectHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface ConvectHistoryRepository extends JpaRepository<ConvectHistory,Long>,
        PagingAndSortingRepository<ConvectHistory,Long>,
        JpaSpecificationExecutor<ConvectHistory> {

    @Query(value = "SELECT SUM(REPLACE(course_to,' ','')::::NUMERIC)/COUNT(*) FROM iteratia.convects as c " +
            "where c.val_curs_date BETWEEN ?1 and ?2 and c.convect_from= ?3 and c.convect_to= ?4",nativeQuery = true)
    BigDecimal getAverageConversionRate(LocalDate after, LocalDate before, String one, String two);

    @Query(value = "SELECT SUM(REPLACE(result,' ','')::::NUMERIC) FROM iteratia.convects as c " +
            "where c.val_curs_date BETWEEN ?1 and ?2 and c.convect_from= ?3 and c.convect_to= ?4",nativeQuery = true)
    BigDecimal getTotalVolumeOfConversions(LocalDate after, LocalDate before, String one, String two);


}
