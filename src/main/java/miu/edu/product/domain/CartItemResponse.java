package miu.edu.product.domain;

import lombok.Data;

import java.util.Set;

@Data
public class CartItemResponse {
    private int id;
    private int userId;
    private int quantity;
    private String name;

    public CartItemResponse(CartItem cartItem){
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.name = cartItem.getProduct().getName();
    }

    public CartItemResponse(CartItem cartItem, int userId){
        this.id = cartItem.getId();
        this.userId = userId;
        this.quantity = cartItem.getQuantity();
        this.name = cartItem.getProduct().getName();
    }
}
