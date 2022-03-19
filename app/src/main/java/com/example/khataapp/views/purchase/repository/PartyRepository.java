package com.example.khataapp.views.purchase.repository;

import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartyRepository {

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
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
                                callBackListener.getServerResponse(response.body(), GET_PARTY);

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
