package com.example.ezycommerce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductFragment extends Fragment {

    private RecyclerView productRecyclerView;

    public ProductFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProductAdapter productAdapter;
        productAdapter = new ProductAdapter(getContext());
        productRecyclerView.setAdapter(productAdapter);

        Retrofit retrofitInstance = ProductApiClient.getInstance();
        ProductService productService = retrofitInstance.create(ProductService.class);
        Call<ProductResponse> productCall = productService.getProduct("2201784674", "jonathanChristopher");

        productCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> productList = response.body().getProducts();
                productAdapter.setProductList(productList);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                productCall.cancel();
            }
        });
        return view;
    }
}