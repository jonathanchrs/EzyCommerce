package com.example.ezycommerce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponse {

    @SerializedName("resultId")
    @Expose
    private Integer productCount;
    @SerializedName("nim")
    @Expose
    private String nim;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("productId")
    @Expose
    private Object productId;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getProductsById(Integer bookId){
        for (Product product : products) {
            if(product.getId() == bookId) return product;
        }
        return null;
    }
}
