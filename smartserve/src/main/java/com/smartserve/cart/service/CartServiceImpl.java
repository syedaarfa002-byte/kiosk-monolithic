
package com.smartserve.cart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartserve.cart.dto.CartItemRequest;
import com.smartserve.cart.dto.CartResponse;
import com.smartserve.cart.entity.Cart;
import com.smartserve.cart.entity.CartItem;
import com.smartserve.cart.repository.CartItemRepository;
import com.smartserve.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // =========================
    // ADD ITEM TO CART
    // =========================
    @Override
    public CartResponse addToCart(Long customerId, CartItemRequest request) {

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart == null) {

            cart = new Cart();
            cart.setCustomerId(customerId);
            cart.setTotalAmount(BigDecimal.ZERO);

            cart = cartRepository.save(cart);
        }

        CartItem item = new CartItem();

        item.setFoodId(request.getFoodId());
        item.setFoodName(request.getFoodName());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());
        item.setCart(cart);

        cartItemRepository.save(item);

        return getCart(customerId);
    }


    // =========================
    // GET CART
    // =========================
    @Override
    public CartResponse getCart(Long customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart == null) {
            return null;
        }

        List<CartItem> items =
                cartItemRepository.findByCartId(cart.getId());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {

            // Prevent NullPointerException
            Integer quantity = item.getQuantity();

            if (quantity == null) {
                quantity = 0;
            }

            if (item.getPrice() != null) {

                BigDecimal itemTotal =
                        item.getPrice()
                            .multiply(BigDecimal.valueOf(quantity));

                total = total.add(itemTotal);
            }
        }

        cart.setTotalAmount(total);
        cartRepository.save(cart);

        List<CartItemRequest> itemResponses = items.stream()
                .map(item -> new CartItemRequest(
                        item.getFoodId(),
                        item.getFoodName(),
                        item.getPrice(),
                        item.getQuantity()))
                .toList();

        return new CartResponse(
                cart.getId(),
                cart.getCustomerId(),
                itemResponses,
                total);
    }


    // =========================
    // UPDATE CART ITEM
    // =========================
    @Override
    public CartResponse updateCartItem(
            Long customerId,
            Long cartItemId,
            Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException(
                    "Cart item does not belong to this cart");
        }

        item.setQuantity(quantity);

        cartItemRepository.save(item);

        return getCart(customerId);
    }


    // =========================
    // REMOVE CART ITEM
    // =========================
    @Override
    public void removeCartItem(
            Long customerId,
            Long cartItemId) {

        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException(
                    "Cart item does not belong to this cart");
        }

        cartItemRepository.delete(item);

        // Recalculate total after deleting the item
        List<CartItem> remainingItems =
                cartItemRepository.findByCartId(cart.getId());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem remainingItem : remainingItems) {

            Integer quantity = remainingItem.getQuantity();

            if (quantity == null) {
                quantity = 0;
            }

            if (remainingItem.getPrice() != null) {

                BigDecimal itemTotal =
                        remainingItem.getPrice()
                                .multiply(
                                    BigDecimal.valueOf(quantity));

                total = total.add(itemTotal);
            }
        }

        cart.setTotalAmount(total);

        cartRepository.save(cart);
    }


    // =========================
    // CLEAR CART
    // =========================
    @Override
    public void clearCart(Long customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart == null) {
            return;
        }

        List<CartItem> items =
                cartItemRepository.findByCartId(cart.getId());

        cartItemRepository.deleteAll(items);

        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepository.save(cart);
    }
}