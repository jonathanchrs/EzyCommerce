package com.example.ezycommerce;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView bookImage;
    private TextView bookTitle, bookPrice, bookDescription, username;
    private Button btnBuy;
    private CartDatabase cartDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        cartDatabase = new CartDatabase(this);

        bookImage = findViewById(R.id.book_image);
        bookTitle = findViewById(R.id.book_title);
        bookPrice = findViewById(R.id.book_price);
        bookDescription = findViewById(R.id.book_description);
        btnBuy = findViewById(R.id.btnBuy);
        username = findViewById(R.id.username);

        Intent intent = getIntent();
        Integer bookId = intent.getIntExtra("bookId", 0);

        Retrofit retrofitInstance = ProductApiClient.getInstance();
        ProductService productService = retrofitInstance.create(ProductService.class);
        Call<ProductResponse> productCall = productService.getProductDetail(bookId, "2201784674", "jonathanChristopher");

        productCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Product product = response.body().getProductsById(bookId);
                Glide.with(getApplicationContext()).load(product.getImg()).into(bookImage);
                bookTitle.setText(product.getName());
                bookPrice.setText("$" + product.getPrice());
                bookDescription.setText(product.getDescription());
                username.setText(response.body().getNama());

                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor cursor = cartDatabase.getCartData();
                        Integer counter = 0;

                        while (cursor.moveToNext()){
                            if(cursor.getInt(0) == product.getId()){
                                counter++;
                                cartDatabase.increaseQuantity(product.getId());
                            }
                        }

                        if(counter == 0){
                            cartDatabase.insertCart(product, 1);
                        }

                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                call.cancel();
            }
        });

    }
}