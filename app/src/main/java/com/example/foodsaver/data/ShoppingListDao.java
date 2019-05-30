package com.example.foodsaver.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Insert
    void insert (ShoppingListItems shoppingListItems);

    @Update
    void update (ShoppingListItems shoppingListItems);

    @Delete
    void delete (ShoppingListItems shoppingListItems);

    @Query("DELETE FROM shopping_list_table")
    void deleteAllShoppingListItems ();

    @Query("SELECT * FROM shopping_list_table ORDER BY shopping_item DESC")
    LiveData<List<ShoppingListItems>> getAllShoppingItems();
}
