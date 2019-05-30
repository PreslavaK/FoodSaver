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

    class FoodsRepository {
        private FoodItemsDao foodItemsDao;
        private LiveData<List<FoodItems>> allFoodItems;

        public FoodsRepository(Application application){
            FoodsDatabase database = FoodsDatabase.getInstance(application);
            foodItemsDao = database.foodItemsDao();
            allFoodItems = foodItemsDao.getAllData();
        }

        public void insert(FoodItems foodItems){
            new InsertFoodsAsyncTask(foodItemsDao).execute(foodItems);
        }

        public void update(FoodItems foodItems){
            new UpdateFoodsAsyncTask(foodItemsDao).execute(foodItems);

        }
        public void delete(FoodItems foodItems){
            new DeleteFoodsAsyncTask(foodItemsDao).execute(foodItems);

        }

        public void deleteAllData(){

            new DeleteAllFoodsAsyncTask(foodItemsDao).execute();
        }

        public LiveData<List<FoodItems>> getAllFoodItems(){
            return allFoodItems;
        }

        private static class InsertFoodsAsyncTask extends AsyncTask<FoodItems, Void, Void> {

            private FoodItemsDao foodItemsDao;

            private InsertFoodsAsyncTask(FoodItemsDao foodItemsDao){
                this.foodItemsDao=foodItemsDao;
            }

            @Override
            protected Void doInBackground(FoodItems... foodItems) {
                foodItemsDao.insert(foodItems[0]);
                return null;
            }
        }


        private static class UpdateFoodsAsyncTask extends AsyncTask<FoodItems, Void, Void> {

            private FoodItemsDao foodItemsDao;

            private UpdateFoodsAsyncTask(FoodItemsDao foodItemsDao){
                this.foodItemsDao=foodItemsDao;
            }

            @Override
            protected Void doInBackground(FoodItems... foodItems) {
                foodItemsDao.update(foodItems[0]);
                return null;
            }
        }

        private static class DeleteFoodsAsyncTask extends AsyncTask<FoodItems, Void, Void> {

            private FoodItemsDao foodItemsDao;

            private DeleteFoodsAsyncTask(FoodItemsDao foodItemsDao){
                this.foodItemsDao=foodItemsDao;
            }

            @Override
            protected Void doInBackground(FoodItems... foodItems) {
                foodItemsDao.delete(foodItems[0]);
                return null;
            }
        }

        private static class DeleteAllFoodsAsyncTask extends AsyncTask<Void, Void, Void> {

            private FoodItemsDao foodItemsDao;

            private DeleteAllFoodsAsyncTask(FoodItemsDao foodItemsDao){
                this.foodItemsDao=foodItemsDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                foodItemsDao.deleteAllFoods();
                return null;
            }
        }
    }

    class ShoppingRepository {

        private ShoppingListDao shoppingListDao;
        private LiveData<List<ShoppingListItems>> allShoppingItems;

        public ShoppingRepository(Application application) {
            FoodsDatabase database = FoodsDatabase.getInstance(application);
            shoppingListDao = database.shoppingListDao();
            allShoppingItems = shoppingListDao.getAllShoppingItems();
        }

        public void insert(ShoppingListItems shoppingListItems) {
            new InsertShoppingListAsyncTask(shoppingListDao).execute(shoppingListItems);
        }

        public void update(ShoppingListItems shoppingListItems) {
            new UpdateShoppingListAsyncTask(shoppingListDao).execute(shoppingListItems);
        }

        public void delete(ShoppingListItems shoppingListItems) {
            new DeleteShoppingListAsyncTask(shoppingListDao).execute(shoppingListItems);
        }

        public void deleteAllShoppingItems() {
            new DeleteAllShoppingListAsyncTask(shoppingListDao).execute();
        }

        public LiveData<List<ShoppingListItems>> getAllShoppingItems() {
            return allShoppingItems;
        }

        private static class InsertShoppingListAsyncTask extends AsyncTask<ShoppingListItems, Void, Void> {

            private ShoppingListDao shoppingListDao;

            private InsertShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
                this.shoppingListDao = shoppingListDao;
            }

            @Override
            protected Void doInBackground(ShoppingListItems... shoppingListItems) {
                shoppingListDao.insert(shoppingListItems[0]);
                return null;
            }
        }

        private static class DeleteShoppingListAsyncTask extends AsyncTask<ShoppingListItems, Void, Void> {

            private ShoppingListDao shoppingListDao;

            private DeleteShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
                this.shoppingListDao = shoppingListDao;
            }

            @Override
            protected Void doInBackground(ShoppingListItems... shoppingListItems) {
                shoppingListDao.delete(shoppingListItems[0]);
                return null;
            }
        }

        private static class UpdateShoppingListAsyncTask extends AsyncTask<ShoppingListItems, Void, Void> {

            private ShoppingListDao shoppingListDao;

            private UpdateShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
                this.shoppingListDao = shoppingListDao;
            }

            @Override
            protected Void doInBackground(ShoppingListItems... shoppingListItems) {
                shoppingListDao.update(shoppingListItems[0]);
                return null;
            }
        }

        private static class DeleteAllShoppingListAsyncTask extends AsyncTask<Void, Void, Void> {

            private ShoppingListDao shoppingListDao;

            private DeleteAllShoppingListAsyncTask(ShoppingListDao shoppingListDao) {
                this.shoppingListDao = shoppingListDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                shoppingListDao.deleteAllShoppingListItems();
                return null;
            }
        }


    }
}
