package com.example.foodsaver.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsaver.R;
import com.example.foodsaver.data.FoodItems;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.FoodItemsHolder> {

    private List<FoodItems> foodItems = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FoodItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pantry_item, parent, false);
        return new FoodItemsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemsHolder holder, int position) {
        FoodItems currentFoodItem = foodItems.get(position);
        holder.textViewTitle.setText(currentFoodItem.getFoodname());

        holder.textViewDate.setText(SimpleDateFormat
                .getDateInstance(SimpleDateFormat.MEDIUM)
                .format(currentFoodItem.getExpdate()));
        holder.textViewQt.setText(String.valueOf(currentFoodItem.getQuantity() + " " + currentFoodItem.getUnits()));
        holder.textViewCategory.setText(currentFoodItem.getCategory());

        switch (currentFoodItem.getCategory()) {
            case "Freezer":
                holder.textViewCategory.setTextColor(Color.BLUE);
                break;
            case "Fridge":
                holder.textViewCategory.setTextColor(Color.MAGENTA);
                break;

            case "Pantry":
                holder.textViewCategory.setTextColor(Color.GREEN);
                break;
        }
    }

    public List<FoodItems> getFoodItems() {
        return foodItems;
    }

    @Override
    public int getItemCount() {
        Log.e("ADAPTER", String.valueOf(foodItems.size()));
        return foodItems.size();
    }

    public void setFoodItems(List<FoodItems> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    public FoodItems getFoodItemAt(int position) {
        return foodItems.get(position);
    }

    class FoodItemsHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDate;
        private TextView textViewQt;
        private TextView textViewCategory;


        public FoodItemsHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewQt = itemView.findViewById(R.id.text_view_quantity);
            textViewCategory = itemView.findViewById(R.id.text_view_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(foodItems.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(FoodItems foodItems);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }


}
