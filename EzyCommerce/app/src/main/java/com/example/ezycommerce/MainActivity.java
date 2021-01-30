package com.example.ezycommerce;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ProductFragment productFragment;
    private Button business, cookbooks, mystery, scifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}