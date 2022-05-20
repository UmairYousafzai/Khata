package com.example.khataapp.views.stockAdjustment.reposiory;

import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_STOCK_ADJUSTMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_STOCK_ADJUSTMENT_BY_CODE;
import static com.example.khataapp.utils.CONSTANTS.SAVE_PURCHASE_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SAVE_STOCK_ADJUSTMENT_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.request.SaveStockAdjustmentRequest;
import com.example.khataapp.models.response.stockAdjustment.GetStockAdjustByCode;
import com.example.khataapp.models.response.stockAdjustment.GetStockAdjustResponse;
import com.example.khataapp.models.response.stockAdjustment.SaveStockAdjustmentResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockAdjustRepository {

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void getStockAdjustList(String businessID) {
        Call<GetDocumentResponse> call = ApiClient.getInstance().getApi().getStockAdjustmentList(businessID);
        call.enqueue(new Callback<GetDocumentResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDocumentResponse> call, @NonNull Response<GetDocumentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            if (callBackListener != null) {
                                callBackListener.getServerResponse(response.body(), GET_STOCK_ADJUSTMENT);

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
            public void onFailure(@NonNull Call<GetDocumentResponse> call, @NonNull Throwable t) {
                callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

            }
        });
    }




    public void getItems(String businessID) {
        Call<GetItemResponse> call = ApiClient.getInstance().getApi().getProducts(businessID);
        call.enqueue(new Callback<GetItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetItemResponse> call, @NonNull Response<GetItemResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        GetItemResponse getItemResponse = response.body();

                        if (getItemResponse.getCode() == 200) {

                            if (getItemResponse.getItem() != null && getItemResponse.getItem().size() > 0) {
                                if (callBackListener != null) {
                                    callBackListener.getServerResponse(getItemResponse, GET_ITEMS);

                                }

                            }
                        } else {
                            callBackListener.getServerResponse(getItemResponse.getMessage(), SERVER_ERROR);
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
            public void onFailure(@NonNull Call<GetItemResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:", t.getMessage());
                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });

    }


    public void saveStockAdjustment(SaveStockAdjustmentRequest request) {
        Call<SaveStockAdjustmentResponse> call = ApiClient.getInstance().getApi().saveStockAdjustDoc(request);

        call.enqueue(new Callback<SaveStockAdjustmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveStockAdjustmentResponse> call, @NonNull Response<SaveStockAdjustmentResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), SAVE_STOCK_ADJUSTMENT_RESPONSE);


                        }
                    }


                } else {
                    if (response.errorBody() != null) {
                        if (callBackListener != null) {
                            callBackListener.getServerResponse(response.errorBody().toString(), SAVE_PURCHASE_ERROR);

                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SaveStockAdjustmentResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SAVE_PURCHASE_ERROR);

                }
            }
        });
    }


    public void getStockAdjustmentByCode(String docCode) {
        Call<GetStockAdjustByCode> call = ApiClient.getInstance().getApi().getStockAdjustmentByCode(docCode);

        call.enqueue(new Callback<GetStockAdjustByCode>() {
            @Override
            public void onResponse(@NonNull Call<GetStockAdjustByCode> call, @NonNull Response<GetStockAdjustByCode> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), GET_STOCK_ADJUSTMENT_BY_CODE);

                        }
                    } else {
                        callBackListener.getServerResponse("Nothing Found", SERVER_ERROR);

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
            public void onFailure(@NonNull Call<GetStockAdjustByCode> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
}
