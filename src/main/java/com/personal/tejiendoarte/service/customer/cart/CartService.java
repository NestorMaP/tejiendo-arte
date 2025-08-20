package com.personal.tejiendoarte.service.customer.cart;

import com.personal.tejiendoarte.dto.AddProductInCartDto;
import com.personal.tejiendoarte.dto.CartItemsDto;
import com.personal.tejiendoarte.dto.OrderDto;
import com.personal.tejiendoarte.dto.PlaceOrderDto;
import com.personal.tejiendoarte.entity.User;

import java.util.List;

public interface CartService {
    public CartItemsDto addProductToCart(AddProductInCartDto addProductInCartDto);

    public OrderDto getCartByUserId(Long userId);

    public OrderDto applyCoupon(Long userId, String code);

    public OrderDto changeProductQuantity(AddProductInCartDto addProductInCartDto, int delta);

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    public void createNewCart(User currentUser);

    public List<OrderDto> getUserPlacedOrders (Long userId);
}
