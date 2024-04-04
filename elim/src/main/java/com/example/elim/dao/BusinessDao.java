package com.example.elim.dao;

import com.example.elim.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BusinessDao extends JpaRepository<Business, Integer> {

    @Query(value = "SELECT * FROM business WHERE car_no =?1 AND date BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Business> findByCarNoAndDate(String carNo, String start, String end);

    @Query(value = "SELECT * FROM business WHERE orderer =?1 AND date BETWEEN ?2 AND ?3", nativeQuery = true)
    List<Business> findByOrdererAndDate(String orderer, String start, String end);
}
