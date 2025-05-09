package com.online.store.repository;

import com.online.store.entity.Inventory;
import com.online.store.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Products product);
}