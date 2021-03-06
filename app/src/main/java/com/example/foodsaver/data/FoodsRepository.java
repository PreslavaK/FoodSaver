package com.example.foodsaver.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodsRepository {
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
