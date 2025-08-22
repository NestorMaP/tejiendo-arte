package com.personal.tejiendoarte.service.customer.wishlist;

import com.personal.tejiendoarte.dto.WishlistDto;

import java.io.IOException;

public interface WishlistService {

    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) throws IOException;

}
