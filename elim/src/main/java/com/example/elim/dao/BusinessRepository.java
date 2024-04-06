package com.example.elim.dao;

import com.example.elim.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Integer>, JpaSpecificationExecutor<Business> {

    @Query(value = "SELECT * FROM business WHERE car_no =?1 AND date BETWEEN ?2 AND ?3  order by  date desc, car_no", nativeQuery = true)
    List<Business> findByCarNoAndDate(String carNo, String start, String end);

    @Query(value = "SELECT * FROM business WHERE orderer =?1 AND date BETWEEN ?2 AND ?3  order by  date desc, car_no", nativeQuery = true)
    List<Business> findByOrdererAndDate(String orderer, String start, String end);
}
