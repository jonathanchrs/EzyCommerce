package com.example.ezycommerce;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ProductFragment productFragment;
    private Button business, cookbooks, mystery, scifi;
    private Toolbar customActionBar;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customActionBar = findViewById(R.id.actionBar);
        username = findViewById(R.id.username);
        setSupportActionBar(customActionBar);

        productFragment = new ProductFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.product_fragment, productFragment).commit();

        business = findViewById(R.id.btnBusiness);
        cookbooks = findViewById(R.id.btnCookbooks);
        mystery = findViewById(R.id.btnMystery);
        scifi = findViewById(R.id.btnScifi);

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessFragment businessFragment = new BusinessFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.product_fragment, businessFragment).commit();
            }
        });

        cookbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookbooksFragment cookbooksFragment = new CookbooksFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.product_fragment, cookbooksFragment).commit();
            }
        });

        mystery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MysteryFragment mysteryFragment = new MysteryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.product_fragment, mysteryFragment).commit();
            }
        });

        scifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScifiFragment scifiFragment = new ScifiFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.product_fragment, scifiFragment).commit();
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
}