package com.example.grocerylist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    String itemName;
    long date;

    Item(String itemName, long date){
        this.itemName = itemName;
        this.date = date;
    }

    public String returnDate(){
        DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        return formatter.format(date);
    }

    public String getItemName() {
        return itemName;
    }

    public long getDate() {
        return date;
    }
}
