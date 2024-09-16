package com.flavorbooking.controllers;

import com.flavorbooking.dtos.response.ListDishResponse;
import com.flavorbooking.dtos.response.ResponseMessage;
import com.flavorbooking.models.ContactUs;
import com.flavorbooking.models.Dish;
import com.flavorbooking.request.ContactRequest;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping(value = "/contact-us")
    public ResponseEntity<?> getAllDishes(@RequestBody ContactRequest request) {
        ResourceResponse response = new ResourceResponse();
        try {
            if (request.getEmail().equals("") || request.getFull_name().equals("")|| request.getSubject().equals("")||request.getMessage().equals("")){
                response.setMessage("Submitted fail, you must fill in full field in that form!");
                response.setSuccess(false);
                response.setData(null);
            }else{
                ContactUs contactUs = new ContactUs();
                contactUs.setEmail(request.getEmail());
                contactUs.setMessage(request.getMessage());
                contactUs.setFullName(request.getFull_name());
                contactUs.setSubject(request.getSubject());
                contactService.saveContact(contactUs);
                response.setMessage("ok");
                response.setSuccess(true);
                response.setData(contactUs);
            }

            return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage( e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}
