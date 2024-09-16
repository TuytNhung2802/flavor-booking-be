package com.flavorbooking.services.impl;

import com.flavorbooking.models.ContactUs;
import com.flavorbooking.repositories.Restaurant.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;
    public void saveContact(ContactUs contactUs){
        contactRepository.save(contactUs);
    }
}
