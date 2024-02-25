package com.obs.testbe.repository;

import com.obs.testbe.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item,String> {
    Page<Item> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
