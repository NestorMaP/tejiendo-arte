package com.personal.tejiendoarte.service.admin.coupon;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;
import com.personal.tejiendoarte.repository.CouponRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;

    public CouponDto createCoupon(Coupon coupon) throws ValidationException {
        if(couponRepository.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon already exists");
        }
    }

}
