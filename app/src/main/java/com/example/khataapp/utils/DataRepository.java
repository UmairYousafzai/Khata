package com.example.khataapp.utils;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.khataapp.database.Dao;
import com.example.khataapp.database.KhataDB;
import com.example.khataapp.models.Party;
import com.example.khataapp.models.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataRepository {

    private final Dao mDao;
    private final LiveData<List<User>> usersList;
    private LiveData<List<Party>> partList;
    private final Executor executor= Executors.newSingleThreadExecutor();

    public DataRepository(Context context)
    {
        mDao= KhataDB.getInstance(context).dao();
        usersList= mDao.getUsers();
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


}
