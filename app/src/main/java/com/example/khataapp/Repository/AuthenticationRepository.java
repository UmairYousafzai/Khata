package com.example.khataapp.Repository;

import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.LOGIN_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.LOGIN_RESPONSE_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.models.GetPartiesServerResponse;
import com.example.khataapp.models.LoginResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository extends BaseRepository {

    public void login(String username, String password) {


        Call<LoginResponse> call = ApiClient.getInstance().getApi().login(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        LoginResponse loginResponse = response.body();

                        if (loginResponse.getCode() == 200) {

                            callBackListener.getServerResponse(response.body(), LOGIN_RESPONSE);
                        } else if (loginResponse.getCode() == 401) {
                            callBackListener.getServerResponse(response.body().getMessage(), LOGIN_RESPONSE_ERROR);

                        }


                    }
                } else {
                    if (response.errorBody() != null) {
                        callBackListener.getServerResponse(response.errorBody().toString(), SERVER_ERROR);
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {


                callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

            }
        });
    }


    public void getPartiesFromServer(String businessID) {
        Call<GetPartiesServerResponse> call;
        for (int i=0 ;i<2;i++)
        {
            if (i==0)
            {
                call = ApiClient.getInstance().getApi().getParties(businessID, "c");

            }else
            {
                 call = ApiClient.getInstance().getApi().getParties(businessID, "s");

            }
            call.enqueue(new Callback<GetPartiesServerResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetPartiesServerResponse> call, @NonNull Response<GetPartiesServerResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getCode() == 200) {
                                if (callBackListener != null) {
                                    callBackListener.getServerResponse(response.body(), GET_PARTY);

                                }
                            } else {
                                callBackListener.getServerResponse(response.body().getMessage(), SERVER_ERROR);

                            }

                        } else {
                            callBackListener.getServerResponse(response.message(), SERVER_ERROR);

                        }
                    } else {
                        if (response.errorBody() != null) {
                            if (callBackListener != null) {
                                callBackListener.getServerResponse(response.errorBody().toString(), SERVER_ERROR);

                            }
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<GetPartiesServerResponse> call, @NonNull Throwable t) {
                    Log.e("Parties Saving Error:", t.getMessage());
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            });
        }




    }

}
