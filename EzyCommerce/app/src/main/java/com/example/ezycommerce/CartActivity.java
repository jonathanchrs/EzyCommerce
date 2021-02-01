package com.example.ezycommerce;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private CartListFragment cartListFragment;
    private CalculatePriceFragment calculatePriceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListFragment = new CartListFragment();
        calculatePriceFragment = new CalculatePriceFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.cart_list_fragment, cartListFragment).replace(R.id.total_price_fragment, calculatePriceFragment).commit();
    }
}