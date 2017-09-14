package com.example.boris.showon.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Boris on 18-Jul-17.
 */

public class RetrofitNetworkManager {
    private static RetrofitNetworkManager instance;

    Retrofit retrofit;
    RetrofitService retrofitService;

    RetrofitNetworkManager(Context context) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.tvmaze.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitNetworkManager getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitNetworkManager(context);
        }
        return instance;
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }


}
