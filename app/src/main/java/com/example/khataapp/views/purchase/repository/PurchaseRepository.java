package com.example.khataapp.views.purchase.repository;

import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.example.khataapp.utils.CONSTANTS.SAVE_PURCHASE_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SAVE_PURCHASE_RETURN_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.Document;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.GetPartiesServerResponse;
import com.example.khataapp.models.GetDocumentByCode;
import com.example.khataapp.models.GetDocumentResponse;
import com.example.khataapp.models.SaveDocumentResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseRepository {
    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void getPurchasesList(String businessID) {
        Call<GetDocumentResponse> call = ApiClient.getInstance().getApi().getPurchasesList(businessID);
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

    public void getPurchaseReturnList(String businessID) {
        Call<GetDocumentResponse> call = ApiClient.getInstance().getApi().getPurchasesReturnList(businessID);
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


        Call<GetPartiesServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID, type);
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


    public void savePurchase(Document document) {
        Call<SaveDocumentResponse> call = ApiClient.getInstance().getApi().savePurchase(document);

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
                            callBackListener.getServerResponse(response.errorBody().toString(), SAVE_PURCHASE_ERROR);

                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SaveDocumentResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SAVE_PURCHASE_ERROR);

                }
            }
        });
    }

    public void savePurchaseReturn(Document document) {
        Call<SaveDocumentResponse> call = ApiClient.getInstance().getApi().savePurchaseReturn(document);

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
                            callBackListener.getServerResponse(response.errorBody().toString(), SAVE_PURCHASE_RETURN_ERROR);

                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<SaveDocumentResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SAVE_PURCHASE_RETURN_ERROR);

                }
            }
        });
    }


    public void getPurchaseByCode(String docCode) {
        Call<GetDocumentByCode> call = ApiClient.getInstance().getApi().getPurchaseByCode(docCode);

        call.enqueue(new Callback<GetDocumentByCode>() {
            @Override
            public void onResponse(@NonNull Call<GetDocumentByCode> call, @NonNull Response<GetDocumentByCode> response) {

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
            public void onFailure(@NonNull Call<GetDocumentByCode> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }    public void getPurchaseReturnByCode(String docCode) {
        Call<GetDocumentByCode> call = ApiClient.getInstance().getApi().getPurchaseReturnByCode(docCode);

        call.enqueue(new Callback<GetDocumentByCode>() {
            @Override
            public void onResponse(@NonNull Call<GetDocumentByCode> call, @NonNull Response<GetDocumentByCode> response) {

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
            public void onFailure(@NonNull Call<GetDocumentByCode> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
}
