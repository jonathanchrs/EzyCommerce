package com.example.ezycommerce;

public class Cart {
    private String cartImage, cartName, cartDescription;
    private Integer quantity, bookId, cartPrice;

    public Cart(String cartImage, String cartName, Integer cartPrice, String cartDescription, Integer quantity, Integer bookId) {
        this.cartImage = cartImage;
        this.cartName = cartName;
        this.cartPrice = cartPrice;
        this.cartDescription = cartDescription;
        this.quantity = quantity;
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public Integer getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Integer cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartDescription() {
        return cartDescription;
    }

    public void setCartDescription(String cartDescription) {
        this.cartDescription = cartDescription;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
