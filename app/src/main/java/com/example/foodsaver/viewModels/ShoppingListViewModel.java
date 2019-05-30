package com.example.foodsaver.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodsaver.data.FoodItemsDao;
import com.example.foodsaver.data.ShoppingListItems;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {
    private FoodItemsDao.ShoppingRepository repository;
    private LiveData<List<ShoppingListItems>> allSItems;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);

        repository = new FoodItemsDao.ShoppingRepository(application);
        allSItems = repository.getAllShoppingItems();
    }

    public void insert (ShoppingListItems shoppingListItems){
        repository.insert(shoppingListItems);
    }

    public void delete (ShoppingListItems shoppingListItems){
        repository.delete(shoppingListItems);
    }
    public void update (ShoppingListItems shoppingListItems){
        repository.update(shoppingListItems);
    }
    public void deleteAll (){
        repository.deleteAllShoppingItems();
    }

    public LiveData<List<ShoppingListItems>> getAllShoppingItems(){
        return allSItems;
    }

}

