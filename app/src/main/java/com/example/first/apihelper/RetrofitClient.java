package com.example.first.apihelper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://bukawaralaba.com/api_android/login")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}