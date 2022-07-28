package com.example.grocerylist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/*
This class just contains basic information about the food item.
It has the date the item was added to the expiration list, the date it will expire, and the item name.
 */
public class Item {
    String itemName;
    long dateExpired;
    long dateAdded;

    Item(String itemName, long dateExpired, long dateAdded){
        this.itemName = itemName;
        this.dateExpired = dateExpired;
        this.dateAdded = dateAdded;
    }

    /*
    returns the expiry date formatted in normal everyday use
    Ex. January 1, 2022
     */
    public String returnDateExpired(){
        DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        return formatter.format(dateExpired);
    }

    /*
    returns the date the item was added but formatted in normal everyday use
    Ex. January 1, 2022
     */
    public String returnDateAdded(){
        DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        return formatter.format(dateAdded);
    }

    public String getItemName() {
        return itemName;
    }

    public long getDateExpired() {
        return dateExpired;
    }

    public long getDateAdded() {
        return dateAdded;
    }
}
