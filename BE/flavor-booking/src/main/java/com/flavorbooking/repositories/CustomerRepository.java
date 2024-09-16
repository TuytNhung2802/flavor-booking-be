//package com.flavorbooking.repositories;
//
//import com.flavorbooking.models.Customer;
//import jakarta.transaction.Transactional;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//    Optional<Customer> findByEmail(String email);
//    Optional<Customer> findById(Integer customerId);
//    Boolean existsByEmail(String email);
//
//    @Modifying
//    @Query(value = "DELETE FROM users_role WHERE users_id = :userId", nativeQuery = true)
//    @Transactional
//    void deleteRole(Integer userId);
//
////    @Query(value = "SELECT u.* FROM users_role ur "
////            + "JOIN user u ON ur.users_id = u.userID "
////            + "JOIN role r ON ur.role_id = r.role_code "
////            + "WHERE r.role_code = :roleCode", nativeQuery = true)
////    List<Customer> findByRoleCode(@Param("roleCode") String roleCode);
//
//    @Query(value = "SELECT c FROM Customer c " +
//            "JOIN c.account a " +
//            "WHERE (:userId IS NULL OR c.id = :userId) " +
//            "AND (:email IS NULL OR c.email LIKE CONCAT('%', :email, '%')) " +
//            "AND (:name IS NULL OR c.fullName LIKE CONCAT('%', :name, '%')) " +
//            "AND (:roleCode IS NULL OR a.roleName = :roleCode)", nativeQuery = false)
//    Page<Customer> findByRoleCodeAndConditions(
//            @Param("userId") Integer userId,
//            @Param("email") String email,
//            @Param("name") String name,
//            @Param("roleCode") String roleCode,
//            Pageable pageable
//    );
//
//}
