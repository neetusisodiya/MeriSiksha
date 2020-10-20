package com.muravtech.merishiksha.server;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.muravtech.merishiksha.server.Config.BASE_URLs;
import static com.muravtech.merishiksha.server.Config.URL;
//import static com.muravtech.merisiksha.server.Config.BASE_URLss;

public class APIClient {
    //   private static AppPreferences mAppPreferences;


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(90, TimeUnit.SECONDS);
        client.writeTimeout(90, TimeUnit.SECONDS);
        client.connectTimeout(900, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URLs)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }

    public static Retrofit getClientservice() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
//    public static Retrofit getClient1() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.readTimeout(90, TimeUnit.SECONDS);
//        client.writeTimeout(90, TimeUnit.SECONDS);
//        client.connectTimeout(900, TimeUnit.SECONDS);
//        client.addInterceptor(interceptor);
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URLss)
//                .client(client.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        return retrofit;
//    }


//    public static Retrofit getClients() {
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.readTimeout(90, TimeUnit.SECONDS);
//        client.writeTimeout(90, TimeUnit.SECONDS);
//        client.connectTimeout(900, TimeUnit.SECONDS);
//        client.addInterceptor(interceptor);
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    // .baseUrl(TEMP_BASE_URL)
//                    .client(client.build())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//
//        return retrofit;
//    }
    public static FormBody.Builder createBuilder(String[] paramsName, String[] paramsValue) {
        FormBody.Builder builder = new FormBody.Builder();

        for (int i = 0; i < paramsName.length; i++) {
            Log.e("values", "createBuilder: " + paramsName[i] + " : " + paramsValue[i]);
            builder.add(paramsName[i], paramsValue[i]);
        }

        return builder;
    }


}
