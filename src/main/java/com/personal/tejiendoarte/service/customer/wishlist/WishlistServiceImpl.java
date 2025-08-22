package com.personal.tejiendoarte.service.customer.wishlist;

import com.personal.tejiendoarte.dto.WishlistDto;
import com.personal.tejiendoarte.repository.WishlistRepository;
import com.personal.tejiendoarte.utils.mapper.WishlistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistMapper wishlistMapper;

    private final WishlistRepository wishlistRepository;

    //TODO: Check as it can register the same product for the same user more than once
    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) throws IOException {
        return wishlistMapper.mapToDto(wishlistRepository.save(wishlistMapper.mapToEntity(wishlistDto)));
    }

}
