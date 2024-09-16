package com.flavorbooking.repositories.User;

import com.flavorbooking.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByCode(String name);

}
