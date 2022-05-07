package com.example.khataapp.Repository;

import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.VOUCHER_RESPONSE;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.models.GetPartiesServerResponse;
import com.example.khataapp.models.response.ServerResponse;
import com.example.khataapp.models.request.Voucher;
import com.example.khataapp.models.response.voucher.VoucherResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartyRepository extends BaseRepository{

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


    public void getVoucherDetails(String partyCode, String businessID) {
        Call<VoucherResponse> call = ApiClient.getInstance().getApi().getVoucherDetail(partyCode, businessID);

        call.enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(@NonNull Call<VoucherResponse> call, @NonNull Response<VoucherResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), VOUCHER_RESPONSE);


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
            public void onFailure(@NonNull Call<VoucherResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }


    public void getPartiesFromServer(String type,String businessID) {



        Call<GetPartiesServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,type);
        call.enqueue(new Callback<GetPartiesServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartiesServerResponse> call, @NonNull Response<GetPartiesServerResponse> response) {
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
            public void onFailure(@NonNull Call<GetPartiesServerResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:",t.getMessage());
                callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

            }
        });



    }

}
