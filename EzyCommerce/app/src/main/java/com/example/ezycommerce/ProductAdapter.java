package com.example.ezycommerce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context context;
    private List<Product> productList;

    public void setProductList(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    public ProductAdapter(Context context){
        this.context = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_item, viewGroup, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder productHolder, int i) {
        productHolder.productNameText.setText(productList.get(i).getName());
        productHolder.producPriceText.setText(productList.get(i).getPrice().toString());
        Glide.with(context).load(productList.get(i).getImg()).into(productHolder.productImageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder{

        private TextView productNameText, producPriceText;
        private ImageView productImageView;

        public ProductHolder(View view){
            super(view);
            productNameText = view.findViewById(R.id.productName);
            producPriceText = view.findViewById(R.id.productPrice);
            productImageView = view. findViewById(R.id.productImage);
        }
    }
}
