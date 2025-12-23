package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.AddCardRequest;
import com.agmkhair.ecom.dto.CartResponse;
import com.agmkhair.ecom.dto.ProductDTO;
import com.agmkhair.ecom.dto.UpdateCartRequest;
import com.agmkhair.ecom.entity.Cart;
import com.agmkhair.ecom.entity.Products;
import com.agmkhair.ecom.repository.CartRepository;
import com.agmkhair.ecom.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final ItemRepository productRepo;

    public Cart addToCart(AddCardRequest req) {

        List<Cart> carts = cartRepo.findByUserIdAndProductIdAndOrderIdIsNull(req.getUserId(), req.getProductId());

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
        cart.setSize(req.getSize());
        cart.setColor(req.getColor());
        cart.setQuantity(req.getQuantity());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        return cartRepo.save(cart);
    }


    public List<CartResponse> getCartByUser(Long userId) {

        List<Cart> carts = cartRepo.findByUserId(userId);

        return carts.stream().map(cart -> {
            CartResponse dto = new CartResponse();

            dto.setId(cart.getId());
            dto.setQuantity(cart.getQuantity());
            dto.setSize(cart.getSize());
            dto.setColor(cart.getColor());

            Products product = cart.getProducts();
            if (product != null) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getTitle());
                productDTO.setPrice(product.getPrice());
                productDTO.setImage(product.getImages());

                dto.setProduct(productDTO);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public List<CartResponse> getCartByUserAndOrderIdNULL(Long userId) {

        List<Cart> carts = cartRepo.findAllByUserIdAndOrderIdIsNull(userId).orElse(null);

        return carts.stream().map(cart -> {
            CartResponse dto = new CartResponse();

            dto.setId(cart.getId());
            dto.setQuantity(cart.getQuantity());
            dto.setSize(cart.getSize());
            dto.setColor(cart.getColor());

            Products product = cart.getProducts();
            if (product != null) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getTitle());
                productDTO.setPrice(product.getPrice());
                productDTO.setImage(product.getImages());

                dto.setProduct(productDTO);
            }

            return dto;
        }).collect(Collectors.toList());
    }



    public Cart updateQuantity(UpdateCartRequest req) {

        return cartRepo.findById(req.getCartId())
                .map(cart -> {
                    cart.setQuantity(req.getQuantity());
                    return cartRepo.save(cart);
                }).orElse(null);
    }


    @Transactional
    public String updateOrder(Long userId, Long orderId) {

        int updated = cartRepo.updateOrderForUser(userId, orderId);

        return updated > 0 ? "Done" : "No open carts found";
    }


    public boolean deleteCartItem(Long cartId) {

        if (!cartRepo.existsById(cartId)) return false;

        cartRepo.deleteById(cartId);
        return true;
    }
}
