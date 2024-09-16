package com.flavorbooking.repositories.Admin;

import com.flavorbooking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Account,Integer> {
 public Boolean existsByEmail(String email);
}
