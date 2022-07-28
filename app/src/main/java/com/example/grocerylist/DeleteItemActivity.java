package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DeleteItemActivity extends AppCompatActivity {

    String itemName;
    long dateExpired;
    long dateAdded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            //retrieve data from MainActivity
            itemName = extras.getString("itemName");
            dateExpired = extras.getLong("dateExpired");
            dateAdded = extras.getLong("dateAdded");

            TextView tvItemName = (TextView) findViewById(R.id.tvItemName);
            TextView tvDateExpired = (TextView) findViewById(R.id.tvDateExpired);
            TextView tvDateAdded = (TextView) findViewById(R.id.tvDateAdded);
            DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");

            //Display information in textview
            tvItemName.setText(itemName);
            tvDateAdded.setText(formatter.format(dateAdded));
            tvDateExpired.setText(formatter.format(dateExpired));
        }

    }

    public void delete(View view){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = getIntent().getExtras();
        intent.putExtra("position", extras.getInt("position"));
        setResult(RESULT_OK, intent);
        finish();
    }
}