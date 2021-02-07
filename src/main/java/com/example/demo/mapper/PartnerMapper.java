package com.example.demo.mapper;

import com.example.demo.entity.Partner;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerMapper {
    Partner getpartnerinfo(String partner_id);
}
