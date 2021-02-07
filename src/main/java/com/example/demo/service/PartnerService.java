package com.example.demo.service;

import com.example.demo.entity.Partner;
import com.example.demo.mapper.PartnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerService {
    @Autowired
    PartnerMapper partnerMapper;

    public Partner getpartner_info(String partner_id){
        return partnerMapper.getpartnerinfo(partner_id);
    }
}
