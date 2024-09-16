package com.flavorbooking.services.impl;

import com.flavorbooking.models.Account;
import com.flavorbooking.repositories.User.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account findAccount(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account updateAccount(Account account) {
        //
        Account updateAccount = accountRepository.findById(account.getId()).get();
        updateAccount.setImage(account.getImage());
        updateAccount.setFullName(account.getFullName());
        updateAccount.setAddress(account.getAddress());
        updateAccount.setPhone(account.getPhone());


        return accountRepository.save(updateAccount);

//    accountRepository.updateCustomer(account.getId(),account.getFullName(),account.getPhone(),account.getAddress(),account.getImage());
    }


    public void updateRoleAccount(Account account) {
        accountRepository.save(account);
//    accountRepository.updateCustomer(account.getId(),account.getFullName(),account.getPhone(),account.getAddress(),account.getImage());
    }

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

}
