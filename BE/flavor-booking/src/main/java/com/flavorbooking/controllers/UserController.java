package com.flavorbooking.controllers;

import com.flavorbooking.models.Account;

import com.flavorbooking.models.Role;
import com.flavorbooking.services.firebase.FirebaseInitialization;
import com.flavorbooking.services.impl.AccountService;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final AccountService accountService;
    private final FirebaseInitialization firebaseInitialization;

    @PutMapping("/edit-profile/{uid}")
    public ResponseEntity<Object> editFrofileCustomer(@PathVariable(value = "uid")Integer uid,
                                                      @RequestParam(value = "full_name")String fullName,
                                                      @RequestParam(value = "phone")String phone,
                                                      @RequestParam(value = "image", required = false) MultipartFile image,
                                                      @RequestParam(value = "address")String address){
        try {
            Account account = accountService.findAccount(uid);
            account.setFullName(fullName);
            account.setPhone(phone);
            account.setAddress(address);
            if(image != null){
                String avatar = firebaseInitialization.uploadFile(image);
                account.setImage(avatar);
            }
            Account saveAccount = accountService.updateAccount(account);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("message","Edit profile successfully");
            response.put("data",saveAccount);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/get-profile/{uid}")
    public ResponseEntity<Object> getFrofileCustomer(@PathVariable(value = "uid") Integer uid){
        try {
            Account account = accountService.findAccount(uid);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("message","Get profile successfully");
            response.put("data",account);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}