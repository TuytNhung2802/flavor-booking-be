package com.flavorbooking.services.impl;

import com.flavorbooking.models.ChatMessage;
import com.flavorbooking.repositories.Restaurant.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    public ChatMessage getList(Integer uid, Integer fid){
        List<ChatMessage> list = chatRepository.getChatByUid(uid, fid);
        if(list.size()>0) return list.get(0);
        else return null;
    }
}
