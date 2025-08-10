package com.personal.tejiendoarte.repository;

import com.personal.tejiendoarte.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByNameContaining(String title);
}
