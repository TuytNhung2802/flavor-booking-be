package com.flavorbooking.services.impl.admin;

import com.flavorbooking.repositories.admin.SupportMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportMessageSerice {
    @Autowired
    private SupportMessageRepository supportmessageRepository;
    public int sumAllSupportMessage(){ return supportmessageRepository.sumAllSupportMessage();}
}
