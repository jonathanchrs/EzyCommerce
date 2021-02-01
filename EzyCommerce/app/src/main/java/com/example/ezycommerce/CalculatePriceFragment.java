package com.example.ezycommerce;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CalculatePriceFragment extends Fragment {

    private TextView subtotal, taxes, total;
    private CartDatabase cartDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculate_price, container, false);

        subtotal = view.findViewById(R.id.subtotal);
        taxes = view.findViewById(R.id.taxes);
        total = view.findViewById(R.id.total);

        cartDatabase = new CartDatabase(getActivity());

        Cursor cursor = cartDatabase.getCartData();

        Integer subtotalValue = 0;
        Double taxesValue = 0.0, totalValue = 0.0;

        while (cursor.moveToNext()){
            subtotalValue = subtotalValue + (cursor.getInt(2) * cursor.getInt(5));
        }

        taxesValue = Double.valueOf(subtotalValue) / 10;
        totalValue = subtotalValue + taxesValue;

        subtotal.setText(subtotalValue.toString());
        taxes.setText(taxesValue.toString());
        total.setText(totalValue.toString());

        return view;
    }
}