package com.example.foodsaver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodsaver.R;

public class AddShoppingItem extends AppCompatActivity {
    public static final String EXTRA_ITEM = "com.example.foodsaver.EXTRA_ITEM";

    EditText editTextName;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_shopping_item);

        editTextName=findViewById(R.id.add_shopping_item);
        buttonAdd = findViewById(R.id.button);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem(){
        String item = editTextName.getText().toString();

        if(item.trim().isEmpty()){
            Toast.makeText(this,"Please insert Item", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ITEM, item);

        setResult(RESULT_OK, data);
        finish();


    }



}
