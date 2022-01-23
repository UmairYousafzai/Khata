package com.example.khataapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.khataapp.models.User;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertUser(User user);

    @Query("Delete from User")
    void deleteUsers( );

    @Query("Select *from user")
    LiveData<List<User>> getUsers();

}
