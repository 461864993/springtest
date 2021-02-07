package com.example.demo.mapper;

import com.example.demo.entity.Activation.Activation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivationMapper {
    Activation getactivationstatus(String activation_code);
    Boolean activatyaccount(Activation activation);
    boolean activationadd(List<Activation> activationList);
    List<Activation> getcodebyowner(int num,int status,String owner);
    List<Activation> getcodebynoowner(int num,int status);
    Boolean setowner(List<Activation> activationList,String owner);
}
