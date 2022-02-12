package com.example.khataapp.department;

import static com.example.khataapp.utils.CONSTANTS.GET_DEPARTMENT;
import static com.example.khataapp.utils.CONSTANTS.SERVER_ERROR;

import androidx.annotation.NonNull;

import com.example.khataapp.Interface.CallBackListener;
import com.example.khataapp.models.GetDepartmentResponse;
import com.example.khataapp.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentRepository {

    private static DepartmentRepository departmentRepository;
    private CallBackListener callBackListener;

    private DepartmentRepository()
    {

    }
    public void setCallBackListener(CallBackListener callBackListener)
    {
        this.callBackListener = callBackListener;
    }

    public static synchronized DepartmentRepository getInstance()
    {
        if (departmentRepository==null)
        {
            departmentRepository= new DepartmentRepository();
        }

        return departmentRepository;
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
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_DEPARTMENT);

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
            public void onFailure(@NonNull Call<GetDepartmentResponse> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }

}
