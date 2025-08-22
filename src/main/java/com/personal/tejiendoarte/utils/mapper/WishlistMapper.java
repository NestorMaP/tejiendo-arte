package com.personal.tejiendoarte.utils.mapper;

import com.personal.tejiendoarte.dto.WishlistDto;
import com.personal.tejiendoarte.entity.Product;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.entity.Wishlist;
import com.personal.tejiendoarte.repository.ProductRepository;
import com.personal.tejiendoarte.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Data
@Component
public class WishlistMapper {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Wishlist mapToEntity(WishlistDto wishlistDto) throws IOException {

        Product product = productRepository.findById(wishlistDto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product " + wishlistDto.getProductId() + " not Found")
                );
        User user = userRepository.findById(wishlistDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User " + wishlistDto.getUserId() + " not Found")
                );

        Wishlist wishlist = new Wishlist();
        wishlist.setProduct(product);
        wishlist.setUser(user);
        return wishlist;
    }

    public WishlistDto mapToDto(Wishlist wishlist) {
        return WishlistDto.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUser().getId())
                .productId(wishlist.getProduct().getId())
                .productName(wishlist.getProduct().getName())
                .productDescription(wishlist.getProduct().getDescription())
                .byteImage(wishlist.getProduct().getByteImage())
                .price(wishlist.getProduct().getPrice())
                .build();
    }
}
