package com.example.khataapp.purchase.repository;

import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_PURCHASES;
import static com.example.khataapp.utils.CONSTANTS.GET_SUPPLIER;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.GetPurchaseResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseRepository {
    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void getPurchases(String businessID)
    {
        Call<GetPurchaseResponse> call = ApiClient.getInstance().getApi().getPurchasesList(businessID);
        call.enqueue(new Callback<GetPurchaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPurchaseResponse> call, @NonNull Response<GetPurchaseResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_PURCHASES);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPurchaseResponse> call, @NonNull Throwable t) {
                callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

            }
        });
    }


    public void getPartiesFromServer(String type,String businessID) {



        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,type);
        call.enqueue(new Callback<GetPartyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartyServerResponse> call, @NonNull Response<GetPartyServerResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_SUPPLIER);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetPartyServerResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:",t.getMessage());
                callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

            }
        });



    }
}
