package com.flavorbooking.repositories.User;

import com.flavorbooking.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    // use Optional for find in UserDetailsService and handle exception by orElseThrow
    Optional<Account> findByUsername(String username);

    Optional<Account>findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

        @Modifying
    @Transactional
    @Query(value = "update account " +
            "set full_name = :fullName," +
            "phone = :phone," +
            "    address = :address," +
            "    image = :image " +
            "    where id = :uid", nativeQuery = true)
    void updateCustomer(@Param("uid")Integer id,
                            @Param("fullName")String fullName,
                            @Param("phone")String phone,
                            @Param("address")String address,
                            @Param("image")String image
                            );
}

