package com.example.ezycommerce;

import android.database.Cursor;
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

public class CartListFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartDatabase cartDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CartAdapter cartAdapter;
        cartAdapter = new CartAdapter(getContext());
        cartRecyclerView.setAdapter(cartAdapter);

        cartDatabase = new CartDatabase(getActivity());
        Cursor cartData = cartDatabase.getCartData();

        ArrayList<Cart> cartList = new ArrayList<>();

        while (cartData.moveToNext()){
            Integer cartId = cartData.getInt(0);
            String cartName = cartData.getString(1);
            Integer cartPrice = cartData.getInt(2);
            String cartDescription = cartData.getString(1);
            String cartImage = cartData.getString(4);
            Integer cartQuantity = cartData.getInt(5);

            Cart cart = new Cart(cartImage, cartName, cartPrice, cartDescription, cartQuantity, cartId);
            cartList.add(cart);
        }

        cartAdapter.setCartList(cartList);

        return view;
    }
}