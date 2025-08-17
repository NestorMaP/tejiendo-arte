package com.personal.tejiendoarte.controller.admin;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;
import com.personal.tejiendoarte.exceptions.ValidationException;
import com.personal.tejiendoarte.service.admin.coupon.AdminCouponService;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final AdminCouponService adminCouponService;

    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            CouponDto createCoupon = adminCouponService.createCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(createCoupon);
        } catch (ValidationException validationException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        }
    }

}
