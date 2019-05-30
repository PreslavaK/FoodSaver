package com.example.foodsaver.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsaver.R;
import com.example.foodsaver.data.ShoppingListItems;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder> {

    private List<ShoppingListItems> items = new ArrayList<>();

    @NonNull
    @Override
    public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);


        return new ShoppingListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListHolder holder, int position) {

        ShoppingListItems currentItem = items.get(position);

        holder.textViewName.setText(currentItem.getShopping_item());
        holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ShoppingListItems> items){
        this.items=items;
        notifyDataSetChanged();
    }

    public ShoppingListItems getItemAt(int position){
        return items.get(position);
    }

    class ShoppingListHolder extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private CheckBox checkBox;

        public ShoppingListHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_shopping_item);
            checkBox = itemView.findViewById(R.id.checkbox_shopping);

        }
    }


}
