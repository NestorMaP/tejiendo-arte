package com.personal.tejiendoarte.service.admin.adminproduct;

import com.personal.tejiendoarte.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface CategoryRepository extends JpaRepository<Category, Long> {
}
