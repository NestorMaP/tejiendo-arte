package com.personal.tejiendoarte.service.admin.coupon;

import com.personal.tejiendoarte.dto.CouponDto;
import com.personal.tejiendoarte.entity.Coupon;
import com.personal.tejiendoarte.exceptions.ValidationException;
import com.personal.tejiendoarte.repository.CouponRepository;
import com.personal.tejiendoarte.utils.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    public CouponDto createCoupon(Coupon coupon) {
        if(couponRepository.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon already exists");
        }
        return couponMapper.mapToDto(couponRepository.save(coupon));
    }

}
