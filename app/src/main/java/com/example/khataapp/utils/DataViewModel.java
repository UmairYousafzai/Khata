package com.example.khataapp.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.khataapp.models.Party;
import com.example.khataapp.models.User;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<User>> userList;
    private LiveData<List<Party>> partyList;

    public DataViewModel(@NonNull Application application) {
        super(application);

        dataRepository = new DataRepository(application.getApplicationContext());
        userList = dataRepository.getUsers();
    }


    public void insertUser(User user) {

        dataRepository.insertUser(user);

    }

    public void deleteUser() {
        dataRepository.deleteUser();

    }

    public LiveData<List<User>> getUsers() {
        return userList;
    }

    public void insertParties(List<Party> partyList) {

        dataRepository.insertParties(partyList);

    }
    public void insertParty(Party party) {

        dataRepository.insertParty(party);

    }

    public void deleteParties() {
        dataRepository.deleteparties();

    }

    public LiveData<List<Party>> getParties(String type) {

        return partyList = dataRepository.getParties(type);

    }

    public void getAndSaveParties()
    {
        dataRepository.getAndSaveParties();
    }
}
