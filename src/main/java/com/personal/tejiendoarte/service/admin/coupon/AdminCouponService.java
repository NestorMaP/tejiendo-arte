package com.personal.tejiendoarte.service.admin.coupon;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;
import com.personal.tejiendoarte.exceptions.ValidationException;

public interface AdminCouponService {

    public CouponDto createCoupon(Coupon coupon);

}
