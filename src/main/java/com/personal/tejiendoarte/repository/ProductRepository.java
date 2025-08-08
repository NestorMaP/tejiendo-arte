package com.personal.tejiendoarte.repository;

import com.personal.tejiendoarte.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
