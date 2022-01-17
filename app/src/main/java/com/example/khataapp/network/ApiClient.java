package com.example.khataapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofitClient;
    private static final String BASE_URL = "https://easyapi.sbstorefsd.com/";
    private static ApiClient mInstance;



    ApiClient() {
        if (retrofitClient == null) {


            retrofitClient =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }


    public static synchronized ApiClient getInstance(){
        if(mInstance==null){
            mInstance = new ApiClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofitClient.create(Api.class);
    }
}
