package com.example.foodsaver.fragments;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodsaver.activities.AddShoppingItem;
import com.example.foodsaver.R;
import com.example.foodsaver.adapters.ShoppingListAdapter;
import com.example.foodsaver.viewModels.ShoppingListViewModel;
import com.example.foodsaver.data.ShoppingListItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    private List<ShoppingListItems> items = new ArrayList<>();

    public static final int ADD_SHOPPING_ITEM_REQUEST = 2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        FloatingActionButton buttonAddShopping = view.findViewById(R.id.button_add_shopping);
        buttonAddShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddShoppingItem.class);
                startActivityForResult(intent, ADD_SHOPPING_ITEM_REQUEST);
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_shopping);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        ShoppingListAdapter adapter = new ShoppingListAdapter();
        recyclerView.setAdapter(adapter);

        shoppingListViewModel = ViewModelProviders.of(getActivity()).get(ShoppingListViewModel.class);
        shoppingListViewModel.getAllShoppingItems().observe(this, new Observer<List<ShoppingListItems>>() {
            @Override
            public void onChanged(List<ShoppingListItems> shoppingListItems) {

                adapter.setItems(shoppingListItems);

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
                shoppingListViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getContext(), "Shopping Item Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
        return view;



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_SHOPPING_ITEM_REQUEST && resultCode == RESULT_OK){
            String item = data.getStringExtra(AddShoppingItem.EXTRA_ITEM);

            ShoppingListItems shoppingListItems = new ShoppingListItems(item, false);
            shoppingListViewModel.insert(shoppingListItems);

            Toast.makeText(getContext(), "Entry saved", Toast.LENGTH_SHORT).show();
        }
    }
}
