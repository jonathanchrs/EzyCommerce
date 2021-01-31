package com.example.ezycommerce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScifiFragment extends Fragment {

    private RecyclerView scifiRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scifi, container, false);

        scifiRecyclerView = view.findViewById(R.id.scifiRecyclerView);
        scifiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProductAdapter productAdapter;
        productAdapter = new ProductAdapter(getContext());
        scifiRecyclerView.setAdapter(productAdapter);

        Retrofit retrofitInstance = ProductApiClient.getInstance();
        ProductService productService = retrofitInstance.create(ProductService.class);
        Call<ProductResponse> productCall = productService.getProduct("2201784674", "jonathanChristopher");

        productCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                List<Product> productList = response.body().getProducts();
                ArrayList<Product> scifiProductList = new ArrayList<>();

                for (Product product : productList) {
                    if(product.getCategory().equals("scifi")){
                        scifiProductList.add(product);
                    }
                }

                productAdapter.setProductList(scifiProductList);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                productCall.cancel();
            }
        });
        return view;
    }
}