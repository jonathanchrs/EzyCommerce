package com.example.ezycommerce;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private CartDatabase cartDatabase;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        productList = new ArrayList<>();
        cartDatabase = new CartDatabase(this);

        Cursor cartData = cartDatabase.getCartData();

        while (cartData.moveToNext()){
            System.out.println(cartData.getString(1));
            System.out.println(cartData.getString(3));
            System.out.println(cartData.getString(4));
        }
    }
}