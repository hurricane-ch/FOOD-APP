package com.foodorderapp.repositories;

import com.foodorderapp.models.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByUserData(String userData);

    List<Order> findAllByIsActiveFalseAndDate(String date);

    List<Order> findAllByIsActiveFalse();

}
