package com.example.khataapp.Repository;

import static com.example.khataapp.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;

import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.models.Voucher;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartyRepository {

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }



    public void saveVoucher(Voucher voucher) {
        Call<ServerResponse> call = ApiClient.getInstance().getApi().saveVoucher(voucher);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), SERVER_RESPONSE);


                        }
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
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }


}
