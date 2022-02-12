package com.example.khataapp.network;

import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.GetLocationResponse;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.LoginResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.PostLocation;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.models.SignUpUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("api/Account/Login")
    Call<LoginResponse> login(@Query("username") String username, @Query("password") String password);

    @GET("api/Location/LocationData")
    Call<GetLocationResponse> getLocations();

    @POST("api/Location/SaveLocation")
    Call<ServerResponse> saveLocation(@Body PostLocation postLocation);

    @POST("api/Account/SignUP")
    Call<ServerResponse> saveUser(@Body SignUpUser signUpUser);

    @POST("api/Party/Party")
    Call<ServerResponse> saveParty(@Body Party party);

    @GET("api/Party/PartyData")
    Call<GetPartyServerResponse> getParties(@Query("BusinessId")String businessID,@Query("partytype") String partyType);

    @GET("api/Department/DepartmentData")
    Call<GetDepartmentResponse> getDepartment(@Query("BusinessId")String businessID);

    @GET("api/Product/ProductData")
    Call<GetItemResponse> getProducts(@Query("BusinessId")String businessID);

    @POST("api/Product/SaveProduct")
    Call<ServerResponse> saveItem(@Body Item item);
}
