package com.example.ezycommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class CartDatabase extends SQLiteOpenHelper {


    public CartDatabase(Context context) {
        super(context, "cart", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE cart (cartId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "productName VARCHAR (50) NOT NULL, " +
                "productPrice INTEGER NOT NULL, " +
                "productDescription VARCHAR (9999) NOT NULL," +
                "productImage VARCHAR (999) NOT NULL," +
                "quantity INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertCart(Product product, Integer quantity){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues cartValues = new ContentValues();
        cartValues.put("productName", product.getName());
        cartValues.put("productPrice", product.getPrice());
        cartValues.put("productDescription", product.getDescription());
        cartValues.put("productImage", product.getImg());
        cartValues.put("quantity", quantity);

        sqLiteDatabase.insert("cart", null, cartValues);
    }

    public Cursor getCartData(){
        SQLiteDatabase cartDb = this.getWritableDatabase();
        Cursor cartData = cartDb.rawQuery("SELECT * FROM cart", null);
        return cartData;
    }
}
