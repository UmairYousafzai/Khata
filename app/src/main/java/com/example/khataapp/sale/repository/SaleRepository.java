package com.example.khataapp.sale.repository;

import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.GetPurchaseByCode;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.models.SaveDocumentResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleRepository {

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void getSaleDocs(int docType ,String businessID) {
        Call<GetDocumentResponse> call = ApiClient.getInstance().getApi().getSaleDocList(docType,businessID);
        call.enqueue(new Callback<GetDocumentResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDocumentResponse> call, @NonNull Response<GetDocumentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            if (callBackListener != null) {
                                callBackListener.getServerResponse(response.body(), GET_DOCUMENT);

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


    public void getPartiesFromServer(String type, String businessID) {


        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID, type);
        call.enqueue(new Callback<GetPartyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartyServerResponse> call, @NonNull Response<GetPartyServerResponse> response) {
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
            public void onFailure(@NonNull Call<GetPartyServerResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:", t.getMessage());
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


    public void saveSaleDocument(Document document) {
        Call<SaveDocumentResponse> call = ApiClient.getInstance().getApi().saveSaleDoc(document);

        call.enqueue(new Callback<SaveDocumentResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveDocumentResponse> call, @NonNull Response<SaveDocumentResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), SAVE_DOCUMENT_RESPONSE);


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
            public void onFailure(@NonNull Call<SaveDocumentResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }


    public void getSaleDocByCode(String docCode) {
        Call<GetPurchaseByCode> call = ApiClient.getInstance().getApi().getSaleDocByCode(docCode);

        call.enqueue(new Callback<GetPurchaseByCode>() {
            @Override
            public void onResponse(@NonNull Call<GetPurchaseByCode> call, @NonNull Response<GetPurchaseByCode> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), GET_DOCUMENT_BY_CODE);

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
            public void onFailure(@NonNull Call<GetPurchaseByCode> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
}
