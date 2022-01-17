package com.example.khataapp.network;

import com.example.khataapp.models.GetLocationResponse;
import com.example.khataapp.models.Location;
import com.example.khataapp.models.PostLocation;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.models.SignUpUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("api/Location/LocationData")
    Call<GetLocationResponse> getLocations();

    @POST("api/Location/SaveLocation")
    Call<ServerResponse> saveLocation(@Body PostLocation postLocation);

    @POST("api/Account/SignUP")
    Call<ServerResponse> saveUser(@Body SignUpUser signUpUser);
}
