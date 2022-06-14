package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> groceryList = new ArrayList<Item>();
    ArrayList<String> itemNames = new ArrayList<String>();
    TwoColumnListAdapter adapter;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        adapter = new TwoColumnListAdapter(this, R.layout.exipration_list, groceryList);
        ListView listview = (ListView) findViewById(R.id.listViewGroceryList);
        listview.setAdapter(adapter);

        Cursor cursor = db.getExpirationList();
        while(cursor.moveToNext()){
            Item item = new Item(cursor.getString(1), Long.valueOf(cursor.getString(2)));
            groceryList.add(item);
        }

    }

    public void addItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String itemName = data.getStringExtra("itemName");
                String date = data.getStringExtra("date");

                boolean result = db.addData(itemName, date);

                if (result){
                    Toast.makeText(MainActivity.this, "Successful write to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unsuccessful write to database", Toast.LENGTH_LONG).show();
                }

                Item item = new Item(itemName, Long.valueOf(date));
                groceryList.add(item);

                for (int i = 0; i < groceryList.size(); i++){
                    System.out.println(groceryList.get(i).itemName + " " + groceryList.get(i).returnDate());
                }

                adapter.notifyDataSetChanged();
            }
        }


    }


}