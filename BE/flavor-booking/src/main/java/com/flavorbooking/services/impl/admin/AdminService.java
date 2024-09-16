package com.flavorbooking.services.impl.admin;

import com.flavorbooking.dtos.UserDTO;
import com.flavorbooking.dtos.response.ResponseMessage;
import com.flavorbooking.exceptions.BadRequestException;
import com.flavorbooking.models.Account;
import com.flavorbooking.repositories.Admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Account> getAllUserList() {
        return adminRepository.findAll();
    }

    public Account getUserAccount(Integer id) {
//        //  1
//        Optional<Account> optionalAccount = adminRepository.findById(id);
//        if (optionalAccount.isPresent()) {
//            Account response = optionalAccount.get(); // get() convert Optional Object to Specify Object
//            return response;
//        }
// 2
        return adminRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + id));

    }

    public void deletebyId(Integer id) {
        adminRepository.deleteById(id);
    }

    public ResponseMessage updateUser( Integer id,UserDTO req) {
        Optional<Account> user = adminRepository.findById(id);
        if (user.isPresent()) {
            Account account = user.get();
            account.setAddress(req.getAddress());
            account.setPhone(req.getPhoneNumber());
            account.setUsername(req.getUserName());
            account.setProvider(req.getProvider());
            account.setFullName(req.getFullName());
            account.setActive(req.getActive());
            if (!account.getEmail().equals(req.getEmail())) {
                if (adminRepository.existsByEmail(req.getEmail())) {
                    throw new BadRequestException("Email is exist");
                }
                account.setEmail(req.getEmail());
            }
            adminRepository.save(account);
            return new ResponseMessage("Update successfully" ,200);
        }
        throw new BadRequestException("Create failed");
    }
}
