package com.example.ezycommerce;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private Context context;
    private List<Cart> cartList;
    private Button increaseQty, decreaseQty;
    private CartDatabase cartDatabase;

    public CartAdapter(Context context){
        this.context = context;
        this.cartList = new ArrayList<>();
    }

    public void setCartList(List<Cart> cartList){
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cart_item, viewGroup, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartHolder cartHolder, int i) {
        Glide.with(context).load(cartList.get(i).getCartImage()).into(cartHolder.cartImage);
        cartHolder.cartName.setText(cartList.get(i).getCartName());
        cartHolder.cartDescription.setText(cartList.get(i).getCartDescription());
        cartHolder.cartPrice.setText("$" + cartList.get(i).getCartPrice().toString());
        cartHolder.cartQuantity.setText(cartList.get(i).getQuantity().toString());

        cartDatabase = new CartDatabase(context);

        increaseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer currentQuantity = cartList.get(cartHolder.getAdapterPosition()).getQuantity();
                cartList.get(cartHolder.getAdapterPosition()).setQuantity(currentQuantity + 1);
                cartDatabase.increaseQuantity(cartList.get(cartHolder.getAdapterPosition()).getBookId());

                Cursor cursor = cartDatabase.getCartData();

                Integer subtotalValue = 0;
                Double taxesValue = 0.0, totalValue = 0.0;

                while (cursor.moveToNext()){
                    subtotalValue = subtotalValue + (cursor.getInt(2) * cursor.getInt(5));
                }

                taxesValue = Double.valueOf(subtotalValue) / 10;
                totalValue = subtotalValue + taxesValue;

                CartActivity cartActivity = (CartActivity) context;
                cartActivity.setPriceTextView(subtotalValue, taxesValue, totalValue);

                notifyDataSetChanged();
            }
        });

        decreaseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer currentQuantity = cartList.get(cartHolder.getAdapterPosition()).getQuantity();

                if(currentQuantity - 1 == 0){
                    System.out.println(cartList.get(cartHolder.getAdapterPosition()).getCartName());
                    System.out.println(cartHolder.getAdapterPosition());
                    cartDatabase.deleteCart(cartList.get(cartHolder.getAdapterPosition()).getBookId());
                    cartList.remove(cartHolder.getAdapterPosition());

                    Cursor cursor = cartDatabase.getCartData();

                    Integer subtotalValue = 0;
                    Double taxesValue = 0.0, totalValue = 0.0;

                    while (cursor.moveToNext()){
                        subtotalValue = subtotalValue + (cursor.getInt(2) * cursor.getInt(5));
                    }

                    taxesValue = Double.valueOf(subtotalValue) / 10;
                    totalValue = subtotalValue + taxesValue;

                    CartActivity cartActivity = (CartActivity) context;
                    cartActivity.setPriceTextView(subtotalValue, taxesValue, totalValue);
                }
                else{
                    cartList.get(cartHolder.getAdapterPosition()).setQuantity(currentQuantity - 1);
                    cartDatabase.decreaseQuantity(cartList.get(cartHolder.getAdapterPosition()).getBookId());

                    Cursor cursor = cartDatabase.getCartData();

                    Integer subtotalValue = 0;
                    Double taxesValue = 0.0, totalValue = 0.0;

                    while (cursor.moveToNext()){
                        subtotalValue = subtotalValue + (cursor.getInt(2) * cursor.getInt(5));
                    }

                    taxesValue = Double.valueOf(subtotalValue) / 10;
                    totalValue = subtotalValue + taxesValue;

                    CartActivity cartActivity = (CartActivity) context;
                    cartActivity.setPriceTextView(subtotalValue, taxesValue, totalValue);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder{

        private TextView cartName, cartDescription, cartPrice;
        private ImageView cartImage;
        private EditText cartQuantity;

        public CartHolder(View view){
            super(view);
            cartImage = view.findViewById(R.id.cartImage);
            cartName = view.findViewById(R.id.cartName);
            cartDescription = view.findViewById(R.id.cartDescription);
            cartPrice = view.findViewById(R.id.cartPrice);
            cartQuantity = view.findViewById(R.id.textQuantity);
            increaseQty = view.findViewById(R.id.increaseQty);
            decreaseQty = view.findViewById(R.id.decreaseQty);
        }
    }
}
