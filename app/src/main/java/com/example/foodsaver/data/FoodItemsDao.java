package com.example.foodsaver.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodItemsDao {

    @Insert
    void insert(FoodItems foodItems);

    @Update
    void update(FoodItems foodItems);

    @Delete
    void delete(FoodItems foodItems);

    @Query("DELETE FROM food_items_table")
    void deleteAllFoods();

    @Query("SELECT*FROM food_items_table")
    LiveData<List<FoodItems>> getAllData();


}
