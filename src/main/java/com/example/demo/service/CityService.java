package com.example.demo.service;

import com.example.demo.entity.City;
import com.example.demo.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    CityMapper cityMapper;

    public List<City> getcitynumber(){return cityMapper.getcitynumber();}

    public  boolean updatecitynumber(int id, int num){return cityMapper.updatecitynumber(id,num);}
}
