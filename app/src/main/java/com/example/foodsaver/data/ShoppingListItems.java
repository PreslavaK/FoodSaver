package com.example.foodsaver.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "shopping_list_table")
public class ShoppingListItems {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String shopping_item;
    private boolean checked;

    public ShoppingListItems(String shopping_item, boolean checked) {
        this.shopping_item = shopping_item;
        this.checked = checked;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setShopping_item(String shopping_item) {
        this.shopping_item = shopping_item;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public long getId() {
        return id;
    }

    public String getShopping_item() {
        return shopping_item;
    }

    public boolean isChecked() {
        return checked;
    }
}
