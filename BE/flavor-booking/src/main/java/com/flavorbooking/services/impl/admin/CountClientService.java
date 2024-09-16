package com.flavorbooking.services.impl.admin;

import com.flavorbooking.repositories.admin.CountClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountClientService {
    @Autowired
    private CountClientRepository countclientRepository;

    public int sumAllCountClient(){
        return countclientRepository.sumAllCountClient();
    }
}
