package com.personal.tejiendoarte.repository;

import com.personal.tejiendoarte.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByCode(String code);

}
