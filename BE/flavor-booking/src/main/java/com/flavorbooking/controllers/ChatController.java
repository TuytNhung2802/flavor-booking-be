package com.flavorbooking.controllers;

import com.flavorbooking.models.Account;
import com.flavorbooking.models.ChatMessage;
import com.flavorbooking.responses.ResourceResponse;
import com.flavorbooking.services.impl.AccountService;
import com.flavorbooking.services.impl.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/friend-chat/{uid}")
    private ResponseEntity<?> listFriend(@PathVariable("uid") Integer uid){
        List<Account> listFriend = accountService.getAllAccount();
        List<ChatMessage> result = new ArrayList<>();

        for(int i=0;i< listFriend.size(); i++){
            if(uid != listFriend.get(i).getId()){
                ChatMessage message = chatService.getList(uid, listFriend.get(i).getId());
                System.out.println(i+" "+ listFriend.get(i).getId());
                if(message!=null) result.add(message);
            }
        }
        ResourceResponse response = new ResourceResponse();
        response.setMessage("ok");
        response.setSuccess(true);
        response.setData(result);
        return new ResponseEntity<ResourceResponse>(response, HttpStatus.ACCEPTED);
    }
}
