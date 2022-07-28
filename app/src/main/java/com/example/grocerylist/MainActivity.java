package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> expirationList = new ArrayList<Item>();
    TwoColumnListAdapter adapter;
    DatabaseHelper db;
    ListView listView;
    String state1 = "chronologicalState";
    String state2 = "alphabeticalState";
    String state3 = "expiryState";
    String sortState = state1;


    int GROCERY_LIST_MAX_SIZE = 100;


    /*
    onCreate just creates a listview and
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create database and retrieve the items from the expiration list
        db = new DatabaseHelper(this);
        Cursor cursor = db.getExpirationList();
        while(cursor.moveToNext()){
            Item item = new Item(cursor.getString(1),
                    Long.valueOf(cursor.getString(2)),
                    Long.valueOf(cursor.getString(3)));
            expirationList.add(item);
        }

        //Instantiate our custom listview adapter and display the items in our expiration list
        adapter = new TwoColumnListAdapter(this, R.layout.exipration_list, expirationList);
        listView = (ListView) findViewById(R.id.listViewGroceryList);
        listView.setAdapter(adapter);

        /*
        Goes to the DeleteItemActivity when the user clicks on one of the items in the listview.
        The DeleteItemActivity just displays basic information about the item and allows the user to delete the item
        they clicked on.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DeleteItemActivity.class);
                intent.putExtra("itemName", expirationList.get(position).itemName);
                intent.putExtra("dateExpired", expirationList.get(position).dateExpired);
                intent.putExtra("dateAdded", expirationList.get(position).dateAdded);
                intent.putExtra("position", position);
                startActivityForResult(intent, 2);
            }
        });

    }

    /*
    Starts the new activity to add an item
     */
    public void addItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, 1);
    }

    /*
    Sorts your listview based on three categories: chronological, the expiry date, and alphabetical
     */
    public void sortList(View view){
        TextView tvSort = (TextView) findViewById(R.id.tvSortTitle);
        switch(sortState){
            case "chronologicalState":

                tvSort.setText("Alphabetical");
                expirationList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return o1.itemName.compareTo(o2.itemName);
                    }
                });
                sortState = state2;
                break;
            case "alphabeticalState":
                tvSort.setText("Expiry Date");
                expirationList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return String.valueOf(o1.dateExpired).compareTo(String.valueOf(o2.dateExpired));
                    }
                });
                sortState = state3;
                break;
            case "expiryState":
                tvSort.setText("Date Added");
                expirationList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return String.valueOf(o1.dateAdded).compareTo(String.valueOf(o2.dateAdded));
                    }
                });
                sortState = state1;
        }

        adapter.notifyDataSetChanged();
    }


    /*
    Retrieves the information from the AddItemActivity or DeleteItemActivity
    and modifies the database based on the information given
    1. add activity
    2. delete activity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        /*
        Code for adding item
         */
        if (requestCode == 1){

            if (resultCode == RESULT_OK){
                if (expirationList.size() <= GROCERY_LIST_MAX_SIZE){
                    //Extract the data from AddItemActivity
                    String itemName = data.getStringExtra("itemName");
                    String dateExpired = data.getStringExtra("dateExpired");
                    String dateAdded = data.getStringExtra("dateAdded");

                    //Create database and save the item information to the database
                    boolean result = db.addData(itemName, dateExpired, dateAdded);
                    if (result){
                        //Add item to expirationList
                        Item item = new Item(itemName, Long.valueOf(dateExpired), Long.valueOf(dateAdded));
                        expirationList.add(item);

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Unable to save item", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "You've reached the limit on how many items you can save!", Toast.LENGTH_LONG).show();
                }
            }

        /*
        Code for deleting item
         */
        } else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                int position = data.getIntExtra("position", -1);
                if (position != -1){
                    int num = db.delete(expirationList.get(position).dateAdded);
                    expirationList.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Deletion unsuccessful. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

        }
    }



}