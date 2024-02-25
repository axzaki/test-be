package com.obs.testbe.repository;

import com.obs.testbe.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,String> {
    Page<Order> findByOrderNo(@Param("orderNo") String orderNo, Pageable pageable);
}
