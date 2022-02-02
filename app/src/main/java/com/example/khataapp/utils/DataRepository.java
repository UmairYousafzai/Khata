package com.example.khataapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.khataapp.database.Dao;
import com.example.khataapp.database.KhataDB;
import com.example.khataapp.models.GetPartyServerResponse;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.User;
import com.example.khataapp.network.ApiClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private final Dao mDao;
    private final LiveData<List<User>> usersList;
    private LiveData<List<Party>> partList;
    private final Executor executor= Executors.newSingleThreadExecutor();
    private Context context;

    public DataRepository(Context context)
    {
        mDao= KhataDB.getInstance(context).dao();
        usersList= mDao.getUsers();
        this.context = context;
    }

    public void insertUser(User user)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertUser(user);
            }
        });
    }

    public void deleteUser( )
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteUsers();
            }
        });
    }

   public LiveData<List<User>> getUsers( )
    {
       return usersList;
    }


    public void insertParties(List<Party> partyList)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insertParties(partyList);
            }
        });
    }

    public void deleteparties( )
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.deleteParty();
            }
        });
    }

   public LiveData<List<Party>> getParties(String type )
    {
       return partList= mDao.getParties(type);
    }

    public void getAndSaveParties()
    {
        getPartiesFromServer("c");
    }



    public String getPartiesFromServer(String type) {


        String businessID= SharedPreferenceHelper.getInstance(context).getBUSINESS_ID();

        Call<GetPartyServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID,type);
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

                                insertParties(getPartyServerResponse.getPartyList());


                            }
                        }

                    }

                }
                else
                {
                    if (response.errorBody() != null) {
                        Toast.makeText(context, ""+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<GetPartyServerResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:",t.getMessage());
            }
        });

        if (type.equals("c"))
        {
            return getPartiesFromServer("s");
        }
        else
        {
            return "break";
        }

    }


}
