package com.example.grocerylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.GroceryList.MESSAGE";
    public static final String EXTRA_DURATION_MILLIS = "com.example.GroceryList.DURATION_MILLIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //Date
        CalendarView date = (CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();

        //set the minimum date to today's date
        date.setMinDate(calendar.getTimeInMillis());

        //program the calendar to select new date when the user touches a new date
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                date.setDate(c.getTimeInMillis());
            }
        });

    }

    /*

     */
    public void addItem(View view){
        Intent intent = new Intent(this, MainActivity.class);

        //Retrieve item name and expiration date and sends it back to MainActivity
        EditText itemName = (EditText) findViewById(R.id.editTextItemName);
        System.out.println(itemName.getText().toString());
        intent.putExtra("itemName", itemName.getText().toString());

        CalendarView date = (CalendarView) findViewById(R.id.calendarView);
        DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        System.out.println(formatter.format(date.getDate()));
        intent.putExtra("date", String.valueOf(date.getDate()));

        setResult(RESULT_OK, intent);
        finish();
    }


}