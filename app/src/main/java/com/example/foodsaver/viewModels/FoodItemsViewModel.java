package com.example.foodsaver.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodsaver.data.FoodItems;
import com.example.foodsaver.data.FoodItemsDao;
import com.example.foodsaver.data.FoodsRepository;

import java.util.List;

public class FoodItemsViewModel extends AndroidViewModel {

   private  FoodsRepository repository;
   private LiveData<List<FoodItems>> allFoods;

   public FoodItemsViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodsRepository(application);
        allFoods = repository.getAllFoodItems();
    }

    public void insert(FoodItems foodItems){
       repository.insert(foodItems);
    }

    public void delete(FoodItems foodItems){
        repository.delete(foodItems);
    }
    public void update(FoodItems foodItems){
        repository.update(foodItems);
    }

     public void deleteAll(){
        repository.deleteAllData();
    }

    public LiveData<List<FoodItems>> getAllFoods(){
       return allFoods;
    }




}
