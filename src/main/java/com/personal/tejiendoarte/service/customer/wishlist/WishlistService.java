package com.personal.tejiendoarte.service.customer.wishlist;

import com.personal.tejiendoarte.dto.WishlistDto;

import java.io.IOException;
import java.util.List;

public interface WishlistService {

    public List<WishlistDto> getWishlistByUserId(Long userid);

    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) throws IOException;

}
