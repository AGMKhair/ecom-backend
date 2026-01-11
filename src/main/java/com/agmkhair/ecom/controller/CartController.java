package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.*;
import com.agmkhair.ecom.entity.Cart;
import com.agmkhair.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    // ADD TO CART
    @PostMapping
    public APIResponse<List<CartResponse>>  addToCart(@RequestBody AddCardRequest req) {

        if (req.getUserId() == null)
        {
            return APIResponseBuilder.failed("Please login");
        }
        List<CartResponse> list = new ArrayList<>();
        Cart cart = service.addToCart(req);

        if (cart != null )
        {
          list = service.getCartByUserAndOrderIdNULL(req.getUserId());
          return APIResponseBuilder.success("Added to cart", list);
        }
        return APIResponseBuilder.internalError("Try Again");
    }

    // GET CART ITEMS BY USER
    @GetMapping("/user/{id}")
    public APIResponse<List<CartResponse>> getByUser(@PathVariable Long id) {

        List<CartResponse> list = service.getCartByUserAndOrderIdNULL(id);

        return APIResponseBuilder.success("Cart items fetched", list);
    }

    // SAME STYLE → /api/carts/user?id=102
    @GetMapping("/user")
    public APIResponse<List<CartResponse>> getByUserQueryParam(@RequestParam Long id) {

        List<CartResponse> list = service.getCartByUserAndOrderIdNULL(id);

        return APIResponseBuilder.success("Cart items fetched", list);
    }

    // UPDATE QUANTITY
    @PutMapping("/Quantity")
    public APIResponse<CartResponse> updateQuantity(@RequestBody UpdateCartRequest req) {

        CartResponse updated = service.updateQuantity(req);

        if (updated == null)
            return APIResponseBuilder.failed("Cart item not found");

        return APIResponseBuilder.success("Quantity updated", updated);
    }


    @PutMapping
    public APIResponse<CartResponse> update(@RequestBody UpdateCartRequest req) {

        CartResponse updated = service.updateQuantity(req);

        if (updated == null)
            return APIResponseBuilder.failed("Cart item not found");

        return APIResponseBuilder.success("Quantity updated", updated);
    }


    // DELETE CART ITEM
    @DeleteMapping("/{id}")
    public APIResponse<Void> deleteCartItem(@PathVariable Long id) {

        boolean deleted = service.deleteCartItem(id);

        return deleted ? APIResponseBuilder.success("Item deleted", null) :
                APIResponseBuilder.failed("Cart item not found");
    }
}