package com.flavorbooking.services.impl;

import com.flavorbooking.models.Role;
import com.flavorbooking.repositories.User.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public Role findRole(Integer id){
        return roleRepository.findById(id).orElse(null);
    }
}
