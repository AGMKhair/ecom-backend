package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.AddCardRequest;
import com.agmkhair.ecom.dto.UpdateCartRequest;
import com.agmkhair.ecom.entity.Cart;
import com.agmkhair.ecom.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;

    public Cart addToCart(AddCardRequest req) {

        List<Cart> carts = cartRepo.findByUserIdAndProductId(req.getUserId(), req.getProductId());

        if (!carts.isEmpty()) {

            // Merge duplicates into the first one
            Cart existing = carts.get(0);
            existing.setQuantity(req.getQuantity());

            // Delete other duplicates if they exist
            if (carts.size() > 1) {
                carts.stream().skip(1).forEach(cartRepo::delete);
            }

            return cartRepo.save(existing);
        }

        // New item
        Cart cart = new Cart();
        cart.setUserId(req.getUserId());
        cart.setProductId(req.getProductId());
        cart.setQuantity(req.getQuantity());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        return cartRepo.save(cart);
    }

    public List<Cart> getCartByUser(Long userId) {
        return cartRepo.findByUserId(userId);
    }

    public Cart updateQuantity(UpdateCartRequest req) {

        return cartRepo.findById(req.getCartId())
                .map(cart -> {
                    cart.setQuantity(req.getQuantity());
                    return cartRepo.save(cart);
                }).orElse(null);
    }

    public boolean deleteCartItem(Long cartId) {

        if (!cartRepo.existsById(cartId)) return false;

        cartRepo.deleteById(cartId);
        return true;
    }
}
