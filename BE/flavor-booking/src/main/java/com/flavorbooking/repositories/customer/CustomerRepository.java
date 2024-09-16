//package com.flavorbooking.repositories.customer;
//
//import com.flavorbooking.models.Customer;
//import jakarta.transaction.Transactional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface CustomerRepository extends JpaRepository<Customer,Integer> {
//    @Modifying
//    @Transactional
//    @Query(value = "update customer c " +
//            "join account a on a.id = c.account_id " +
//            "set c.full_name = :fullName, " +
//            "c.phone = :phone, " +
//            "c.email = :email, " +
//            "c.address = :address, " +
//            "c.verify = :verify, " +
//            "c.image = :image, " +
//            "a.username = :username " +
//            " where c.id = :uid ", nativeQuery = true)
//    void updateCustomer(@Param("uid")Integer id,
//                            @Param("fullName")String fullName,
//                            @Param("phone")String phone,
//                            @Param("email")String email,
//                            @Param("verify")Integer verify,
//                            @Param("address")String address,
//                            @Param("image")String image,
//                            @Param("username")String username
//                            );
//
//    boolean existsByEmail(String email);
//
//    Customer findByEmail(String email);
//
//
//
//}
