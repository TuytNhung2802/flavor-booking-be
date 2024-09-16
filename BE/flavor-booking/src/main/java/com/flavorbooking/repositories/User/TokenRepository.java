package com.flavorbooking.repositories.User;

import com.flavorbooking.models.Account;
import com.flavorbooking.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByAccount(Account account);

    Token findByAccessToken(String accessToken);



}
