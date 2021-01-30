package com.example.ezycommerce;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductApiClient {

    private static Retrofit instance;
    private static String baseUrl = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/";

     public static Retrofit getInstance(){
         if(instance == null) instance = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
         return instance;
     }

}
