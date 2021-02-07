package com.example.demo.mapper;

import com.example.demo.entity.City;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityMapper {
    List<City> getcitynumber();
    boolean updatecitynumber (int id,int num);
}
