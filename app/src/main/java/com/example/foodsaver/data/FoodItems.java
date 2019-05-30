package com.example.foodsaver.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "food_items_table")
public class FoodItems {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String foodname;
    private double quantity;
    private String units;
    private String category;
    private long expdate;

    public FoodItems(String foodname, double quantity, String units, long expdate, String category) {
        this.foodname = foodname;
        this.quantity = quantity;
        this.units = units;
        this.expdate = expdate;
        this.category= category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFoodname() {
        return foodname;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnits() {
        return units;
    }

    public long getExpdate() {
        return expdate;
    }

    public String getCategory() {
        return category;
    }
}
