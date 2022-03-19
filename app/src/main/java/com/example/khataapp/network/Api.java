package com.example.khataapp.network;

import com.example.khataapp.models.Department;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.models.GetGroupResponse;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.GetLocationResponse;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.GetDocumentByCode;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.models.Group;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.LoginResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.PostLocation;
import com.example.khataapp.models.SaveDepartmentResponse;
import com.example.khataapp.models.SaveGroupResponse;
import com.example.khataapp.models.SaveDocumentResponse;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.models.SignUpUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    /****************************   Authentication **********************/
    @GET("api/Account/Login")
    Call<LoginResponse> login(@Query("username") String username, @Query("password") String password);

    @GET("api/Location/LocationData")
    Call<GetLocationResponse> getLocations();

    @POST("api/Location/SaveLocation")
    Call<ServerResponse> saveLocation(@Body PostLocation postLocation);

    @POST("api/Account/SignUP")
    Call<ServerResponse> saveUser(@Body SignUpUser signUpUser);


    /****************************   Party **********************/


    @POST("api/Party/Party")
    Call<ServerResponse> saveParty(@Body Party party);

    @GET("api/Party/PartyData")
    Call<GetPartyServerResponse> getParties(@Query("BusinessId")String businessID,@Query("partytype") String partyType);

    /****************************   Department , Group And Sub Group **********************/


    @GET("api/Department/DepartmentData")
    Call<GetDepartmentResponse> getDepartment(@Query("BusinessId")String businessID);

    @POST("api/Department/SaveDepartment")
    Call<SaveDepartmentResponse> saveDepartment(@Body Department department);


    @GET("api/Group/GroupData")
    Call<GetGroupResponse> getGroupList(@Query("BusinessId")String businessID, @Query("DepartmentCode")String departmentCode);

    @POST("api/SubGroup/SaveSubGroup")
    Call<SaveGroupResponse> saveGroup(@Body Group group);

    /****************************   Items **********************/

    @GET("api/Product/ProductData")
    Call<GetItemResponse> getProducts(@Query("BusinessId")String businessID);

    @POST("api/Product/SaveProduct")
    Call<ServerResponse> saveItem(@Body Item item);

    /****************************   Purchases **********************/


    @GET("api/Purchase/PurchaseData")
    Call<GetDocumentResponse> getPurchasesList(@Query("BusinessId")String businessID);

    @GET("api/PurchaseReturn/PurchaseReturnData")
    Call<GetDocumentResponse> getPurchasesReturnList(@Query("BusinessId")String businessID);

    @GET("api/Purchase/PurchaseByCode")
    Call<GetDocumentByCode> getPurchaseByCode(@Query("DocNo")String docNO);

    @GET("api/PurchaseReturn/PurchaseReturnByCode")
    Call<GetDocumentByCode> getPurchaseReturnByCode(@Query("DocNo")String docNO);

    @POST("api/Purchase/SavePurchase")
    Call<SaveDocumentResponse> savePurchase(@Body Document document);

    @POST("api/PurchaseReturn/SavePurchaseReturn")
    Call<SaveDocumentResponse> savePurchaseReturn(@Body Document document);



    /****************************   Sale **********************/


    @GET("api/Sale/SaleData")
    Call<GetDocumentResponse> getSaleDocList(@Query("DocType")int docType, @Query("BusinessId")String businessID);

    @GET("api/Sale/SaleByCode")
    Call<GetDocumentByCode> getSaleDocByCode(@Query("DocNo")String docNO);

    @POST("api/Sale/SaveSale")
    Call<SaveDocumentResponse> saveSaleDoc(@Body Document document);
}
