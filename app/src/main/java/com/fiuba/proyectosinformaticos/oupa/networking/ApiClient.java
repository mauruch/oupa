package com.fiuba.proyectosinformaticos.oupa.networking;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private final static String API_BASE_URL = "http://oupa-api.us-east-2.elasticbeanstalk.com/";
    private static ApiClient instance;
    private Retrofit retrofit;

    private ApiClient() {
        buildRetrofit();
    }

    public synchronized static ApiClient getInstance() {
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }

    private Retrofit buildRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder().addHeader("Authorization", "value").build();
                return chain.proceed(request);
            }
        });

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public OupaApi getOupaClient() {
        return retrofit.create(OupaApi.class);
    }
}