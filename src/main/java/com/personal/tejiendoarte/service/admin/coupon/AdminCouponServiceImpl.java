package com.personal.tejiendoarte.service.admin.coupon;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;
import com.personal.tejiendoarte.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;

    public CouponDto createCoupon(Coupon coupon) {

    }

}
