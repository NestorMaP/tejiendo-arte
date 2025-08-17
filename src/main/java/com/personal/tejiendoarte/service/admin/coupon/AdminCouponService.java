package com.personal.tejiendoarte.service.admin.coupon;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;

import java.util.List;

public interface AdminCouponService {

    public List<CouponDto> getAllCoupons();

    public CouponDto createCoupon(Coupon coupon);

}
