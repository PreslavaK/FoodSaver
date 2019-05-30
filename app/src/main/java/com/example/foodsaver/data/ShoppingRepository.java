package com.example.foodsaver.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingRepository {
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
