package com.happylife.library.myspringbootproject.repository;

import com.happylife.library.myspringbootproject.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM ORDERS", nativeQuery = true)
    List<Orders> findAll();

}
