package com.example.foodsaver.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodsaver.activities.AddEditFoodActivity;
import com.example.foodsaver.adapters.FoodItemsAdapter;
import com.example.foodsaver.viewModels.FoodItemsViewModel;
import com.example.foodsaver.R;
import com.example.foodsaver.data.FoodItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PantryListFragment extends Fragment {

    // Tutorial for all architectural components and database related stuff:
    // https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

    public static final int ADD_ITEM_REQUEST =1;
    public static final int EDIT_ITEM_REQUEST =2;

    private FoodItemsViewModel foodItemsViewModel;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_list, container, false);

        FloatingActionButton buttonAddEntry = view.findViewById(R.id.button_add_food);
        buttonAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEditFoodActivity.class);

                startActivityForResult(intent, ADD_ITEM_REQUEST);
            }
        });

        RecyclerView recyclerView =  view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final FoodItemsAdapter adapter = new FoodItemsAdapter();
        recyclerView.setAdapter(adapter);

        foodItemsViewModel = ViewModelProviders.of(getActivity()).get(FoodItemsViewModel.class);
        foodItemsViewModel.getAllFoods().observe(this, new Observer<List<FoodItems>>() {
            @Override
            public void onChanged(List<FoodItems> foodItems) {
                adapter.setFoodItems(foodItems);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                foodItemsViewModel.delete(adapter.getFoodItemAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getContext(), "Food Item Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new FoodItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodItems foodItems) {
                Intent intent = new Intent(getActivity(), AddEditFoodActivity.class);

                intent.putExtra(AddEditFoodActivity.EXTRA_ID, foodItems.getId());
                intent.putExtra(AddEditFoodActivity.EXTRA_NAME, foodItems.getFoodname());
                intent.putExtra(AddEditFoodActivity.EXTRA_CATEGORY, foodItems.getCategory());
                intent.putExtra(AddEditFoodActivity.EXTRA_DATE, foodItems.getExpdate());
                intent.putExtra(AddEditFoodActivity.EXTRA_QUANTITY, foodItems.getQuantity());
                intent.putExtra(AddEditFoodActivity.EXTRA_UNIT, foodItems.getUnits());

                startActivityForResult(intent, EDIT_ITEM_REQUEST);
            }
        });
        

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("ActivityResult","CALLED");
        if((requestCode == ADD_ITEM_REQUEST) && (resultCode == Activity.RESULT_OK)){
            String name = data.getStringExtra(AddEditFoodActivity.EXTRA_NAME);
            double quantity = data.getDoubleExtra(AddEditFoodActivity.EXTRA_QUANTITY, 0);
            String units = data.getStringExtra(AddEditFoodActivity.EXTRA_UNIT);
            String category = data.getStringExtra(AddEditFoodActivity.EXTRA_CATEGORY);
            long date = data.getLongExtra(AddEditFoodActivity.EXTRA_DATE, 0);

            FoodItems foodItems = new FoodItems(name,quantity,units,date,category);
            foodItemsViewModel.insert(foodItems);

            Toast.makeText(getContext(),"Entry added", Toast.LENGTH_SHORT).show();

        }else if((requestCode == EDIT_ITEM_REQUEST) && (resultCode == Activity.RESULT_OK)) {
            int id = data.getIntExtra(AddEditFoodActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "The entry cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditFoodActivity.EXTRA_NAME);
            double quantity = data.getDoubleExtra(AddEditFoodActivity.EXTRA_QUANTITY, 0);
            String units = data.getStringExtra(AddEditFoodActivity.EXTRA_UNIT);
            String category = data.getStringExtra(AddEditFoodActivity.EXTRA_CATEGORY);
            long date = data.getLongExtra(AddEditFoodActivity.EXTRA_DATE, 0);
            FoodItems foodItems = new FoodItems(name, quantity, units, date, category);
            foodItems.setId(id);

            foodItemsViewModel.update(foodItems);
            Toast.makeText(getContext(),"Entry Updated", Toast.LENGTH_SHORT).show();


        }

        else{
            Toast.makeText(getContext(),"Entry NOT added", Toast.LENGTH_SHORT).show();

        }
    }
}
