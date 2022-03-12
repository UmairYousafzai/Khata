package com.example.khataapp.stock.repository;

import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.GET_ITEMS;
import static com.example.khataapp.utils.CONSTANTS.GET_PARTY;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;
import static com.example.khataapp.utils.CONSTANTS.SERVER_RESPONSE;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.database.Dao;
import com.example.khataapp.database.KhataDB;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.models.GetItemResponse;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Item;
import com.example.khataapp.models.ServerResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockRepository {
    private static StockRepository stockRepository;
    private Dao mDao;
    private CallBackListener callBackListener;


    public StockRepository(Context context)
    {
        mDao= KhataDB.getInstance(context).dao();
    }

    public void setCallBackListener(CallBackListener callBackListener)
    {
        this.callBackListener = callBackListener;
    }

    public synchronized static StockRepository getInstance(Context context)
    {
        if (stockRepository==null)
        {
            stockRepository = new StockRepository(context);
        }

        return stockRepository;
    }

    public void saveItemToServer(Item item)
    {
        Call<ServerResponse> call = ApiClient.getInstance().getApi().saveItem(item);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.body(),SERVER_RESPONSE);

                        }
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
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {

                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }
            }
        });
    }

    public void getDepartmentFromServer(String businessId)
    {

        Call<GetDepartmentResponse> call = ApiClient.getInstance().getApi().getDepartment(businessId);

        call.enqueue(new Callback<GetDepartmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDepartmentResponse> call, @NonNull Response<GetDepartmentResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.body(), GET_DEPARTMENT);

                        }
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
            public void onFailure(@NonNull Call<GetDepartmentResponse> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }

    public void getSupplier(String businessID)
    {
        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,"s");
        call.enqueue(new Callback<GetPartyServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartyServerResponse> call, @NonNull Response<GetPartyServerResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        GetPartyServerResponse getPartyServerResponse= response.body();

                        if (getPartyServerResponse.getCode()==200)
                        {

                            if (getPartyServerResponse.getPartyList()!=null&& getPartyServerResponse.getPartyList().size()>0)
                            {
                                if (callBackListener!=null)
                                {
                                    callBackListener.getServerResponse(getPartyServerResponse.getPartyList(), GET_PARTY);

                                }

                            }
                        }

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
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }
            }
        });

    }
    public void getItems(String businessID)
    {
        Call<GetItemResponse> call = ApiClient.getInstance().getApi().getProducts(businessID);
        call.enqueue(new Callback<GetItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetItemResponse> call, @NonNull Response<GetItemResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        GetItemResponse getItemResponse= response.body();

                        if (getItemResponse.getCode()==200)
                        {

                            if (getItemResponse.getItem()!=null&& getItemResponse.getItem().size()>0)
                            {
                                if (callBackListener!=null)
                                {
                                    callBackListener.getServerResponse(getItemResponse.getItem(),GET_ITEMS);

                                }

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(getItemResponse.getMessage(),SERVER_ERROR);
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
            public void onFailure(@NonNull Call<GetItemResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:",t.getMessage());
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }
            }
        });

    }


}
