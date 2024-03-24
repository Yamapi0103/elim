package com.example.elim.dao;

import com.example.elim.model.Business;
import org.springframework.data.repository.CrudRepository;

public interface BusinessDao extends CrudRepository<Business, Integer> {
}
