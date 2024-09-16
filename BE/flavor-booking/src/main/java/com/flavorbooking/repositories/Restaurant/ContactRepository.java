package com.flavorbooking.repositories.Restaurant;

import com.flavorbooking.models.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactUs, Integer> {
}
