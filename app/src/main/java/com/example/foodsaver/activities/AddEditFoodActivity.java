package com.example.foodsaver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodsaver.fragments.DatePickerFragment;
import com.example.foodsaver.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddEditFoodActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String EXTRA_ID = "com.example.foodsaver.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.foodsaver.EXTRA_NAME";
    public static final String EXTRA_QUANTITY = "com.example.foodsaver.EXTRA_QUANTITY";
    public static final String EXTRA_UNIT = "com.example.foodsaver.EXTRA_UNIT";
    public static final String EXTRA_CATEGORY = "com.example.foodsaver.EXTRA_CATEGORY";
    public static final String EXTRA_DATE = "com.example.foodsaver.EXTRA_DATE";

    private static final int CAMERA_PERMISSION_REQUEST = 0;


    private EditText editTextName, editTextDate, editTextQuantity, editTextUnits;
    private Spinner spinnerCategory;
    private ImageButton imageButtonCalendar;
    private long dateInMilis = 0;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        editTextName = findViewById(R.id.edit_food_name);
        editTextQuantity = findViewById(R.id.edit_quantity);
        editTextUnits = findViewById(R.id.edit_units);
        editTextDate = findViewById(R.id.edit_expiration);
        spinnerCategory = findViewById(R.id.spinner_category);
        imageButtonCalendar = findViewById(R.id.image_button_calendar);
        requestQueue = Volley.newRequestQueue(this);

        //Populate Spinner
        ArrayList<String> categories = new ArrayList<String>();
        categories.add(getString(R.string.freezer));
        categories.add(getString(R.string.fridge));
        categories.add(getString(R.string.pantry));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);

        //
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        // EDIT TEXT
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Food Item");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            //!
            editTextDate.setText(intent.getStringExtra(EXTRA_DATE));
            editTextQuantity.setText(intent.getStringExtra(EXTRA_QUANTITY));
            editTextUnits.setText(intent.getStringExtra(EXTRA_UNIT));
            spinnerCategory.setVerticalScrollbarPosition(categories.indexOf(intent.getStringExtra(EXTRA_NAME)));

        } else {
            setTitle("Add Food Item");
        }

        //DatePicker
        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    //Menu Items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_food_menu, menu);
        return true;
    }


    private void saveEntry() {
        String name = editTextName.getText().toString();
        double quantity = Double.parseDouble(editTextQuantity.getText().toString());
        String units = editTextUnits.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        long date = dateInMilis;

        if (name.trim().isEmpty() || date == 0) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_QUANTITY, quantity);
        data.putExtra(EXTRA_UNIT, units);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_DATE, date);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    //Find which menu item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_entry:
                saveEntry();
                return true;
            case R.id.barccde_reader:
                openCamera();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        editTextDate.setText(DateFormat.getDateInstance().format((calendar.getTime())));

        dateInMilis = calendar.getTimeInMillis();
    }

    //barcode scanner
    public boolean openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.initiateScan();
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Log.e("theTag", scanResult.toString());
            String[] upcArr = scanResult.toString().split("\n");
            String[] theUpc = upcArr[1].split(": ");
            Log.e("theTag", theUpc[1]);
            String url = "https://api.upcitemdb.com/prod/trial/lookup?upc=" + theUpc[1];

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("items");

                                for (int i =0; i < jsonArray.length();i++){
                                   JSONObject name = jsonArray.getJSONObject(i);

                                   String foodname = name.getString("title");

                                    Log.e("Url Result", foodname);

                                    editTextName.setText(foodname);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


            requestQueue.add(jsonObjectRequest);




        }
    }
}
