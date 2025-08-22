package com.personal.tejiendoarte.repository;

import com.personal.tejiendoarte.entity.FAQ;
import com.personal.tejiendoarte.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByProductId(Long productId);
}
