package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> expirationList = new ArrayList<Item>();
    ArrayList<String> itemNames = new ArrayList<String>();
    TwoColumnListAdapter adapter;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create database and retrieve the items from the expiration list
        db = new DatabaseHelper(this);
        Cursor cursor = db.getExpirationList();
        while(cursor.moveToNext()){
            Item item = new Item(cursor.getString(1), Long.valueOf(cursor.getString(2)));
            expirationList.add(item);
        }

        //Instantiate our custom listview adapter and display the items in our expiration list
        adapter = new TwoColumnListAdapter(this, R.layout.exipration_list, expirationList);
        ListView listview = (ListView) findViewById(R.id.listViewGroceryList);
        listview.setAdapter(adapter);

    }

    /*
    Starts the new activity to add an item
     */
    public void addItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, 1);
    }

    /*
    Retrieves the information from the AddItemActivity
    and adds that new information to the database and expirationList for displaying
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                //Extract the data
                String itemName = data.getStringExtra("itemName");
                String date = data.getStringExtra("date");

                //Create database and add save the item information
                boolean result = db.addData(itemName, date);
                if (result){
                    Toast.makeText(MainActivity.this, "Successful write to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unsuccessful write to database", Toast.LENGTH_LONG).show();
                }

                //Add item to expirationList
                Item item = new Item(itemName, Long.valueOf(date));
                expirationList.add(item);

//                for (int i = 0; i < expirationList.size(); i++){
//                    System.out.println(expirationList.get(i).itemName + " " + expirationList.get(i).returnDate());
//                }

                adapter.notifyDataSetChanged();
            }
        }


    }


}