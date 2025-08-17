package com.personal.tejiendoarte.controller.admin;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;
import com.personal.tejiendoarte.exceptions.ValidationException;
import com.personal.tejiendoarte.service.admin.coupon.AdminCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final AdminCouponService adminCouponService;

    @GetMapping
    public ResponseEntity<List<CouponDto>> getAllCoupons() {
        return ResponseEntity.status(HttpStatus.OK).body(adminCouponService.getAllCoupons());
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            CouponDto createCoupon = adminCouponService.createCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(createCoupon);
        } catch (ValidationException validationException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        }
    }

}
