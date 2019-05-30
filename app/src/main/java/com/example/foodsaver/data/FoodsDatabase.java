package com.example.foodsaver.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FoodItems.class, ShoppingListItems.class}, version = 1)
public abstract class FoodsDatabase extends RoomDatabase {

    private static FoodsDatabase instance;

    public abstract FoodItemsDao foodItemsDao();
    public  abstract ShoppingListDao shoppingListDao();

    public static synchronized FoodsDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            FoodsDatabase.class, "food_items_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // populate database
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void,Void> {
        private FoodItemsDao foodItemsDao;
        private ShoppingListDao shoppingListDao;

        private PopulateDbAsyncTask (FoodsDatabase db){
            foodItemsDao = db.foodItemsDao();
            shoppingListDao = db.shoppingListDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodItemsDao.insert(new FoodItems("Food Name", 100.0, "units", 234545, "Fridge"));
            shoppingListDao.insert(new ShoppingListItems("Apples", false));

            /*private String foodname;
    private float quantity;
    private String units;
    private String category;
    private long expdate; */

            return null;
        }
    }


}
