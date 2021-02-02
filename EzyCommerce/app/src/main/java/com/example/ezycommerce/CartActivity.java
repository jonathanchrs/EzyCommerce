package com.example.ezycommerce;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private TextView subtotal, taxes, total, username;
    private Button btnCancel, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartAdapter cartAdapter;
        cartAdapter = new CartAdapter(this);
        cartRecyclerView.setAdapter(cartAdapter);

        setCartList(cartAdapter);

        subtotal = findViewById(R.id.subtotal);
        taxes = findViewById(R.id.taxes);
        total = findViewById(R.id.total);
        username = findViewById(R.id.username);

        setPriceCalculation();

        btnNext = findViewById(R.id.btnNext);
        btnCancel = findViewById(R.id.btnCancel);

        CartDatabase cartDatabase = new CartDatabase(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                cartDatabase.deleteAllCart();
                Toast.makeText(CartActivity.this, "Transaction Success !", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Retrofit retrofitInstance = ProductApiClient.getInstance();
        ProductService productService = retrofitInstance.create(ProductService.class);
        Call<ProductResponse> productCall = productService.getProduct("2201784674", "jonathanChristopher");

        productCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                username.setText(response.body().getNama());
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                productCall.cancel();
            }
        });
    }

    private void setCartList(CartAdapter cartAdapter) {
        CartDatabase cartDatabase = new CartDatabase(this);
        Cursor cartData = cartDatabase.getCartData();

        ArrayList<Cart> cartList = new ArrayList<>();

        while (cartData.moveToNext()){
            Integer cartId = cartData.getInt(0);
            String cartName = cartData.getString(1);
            Integer cartPrice = cartData.getInt(2);
            String cartDescription = cartData.getString(6);
            String cartImage = cartData.getString(4);
            Integer cartQuantity = cartData.getInt(5);

            Cart cart = new Cart(cartImage, cartName, cartPrice, cartDescription, cartQuantity, cartId);
            cartList.add(cart);
        }

        cartAdapter.setCartList(cartList);
    }

    public void setPriceTextView(Integer subtotalValue, Double taxesValue, Double totalValue){
        subtotal.setText("$" + subtotalValue.toString());
        taxes.setText("$" + taxesValue.toString());
        total.setText("$" + totalValue.toString());
    }

    public void setPriceCalculation(){
        CartDatabase cartDatabase = new CartDatabase(this);
        Cursor cartData = cartDatabase.getCartData();

        Integer subtotalValue = 0;
        Double taxesValue = 0.0, totalValue = 0.0;

        while (cartData.moveToNext()){
            subtotalValue = subtotalValue + (cartData.getInt(2) * cartData.getInt(5));
        }

        taxesValue = Double.valueOf(subtotalValue) / 10;
        totalValue = subtotalValue + taxesValue;

        subtotal.setText("$" + subtotalValue.toString());
        taxes.setText("$" + taxesValue.toString());
        total.setText("$" + totalValue.toString());
    }
}