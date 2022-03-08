package com.example.fixme;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * shows all the data in the database and uses a button to open Add reminder activity
 */

public class ReminderPage extends AppCompatActivity {
    DatabaseHelper objMyDB;

    /**
     * displays the items saved in the database to listview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_page);
        final ListView listView = findViewById(R.id.reminder_list);
        objMyDB = new DatabaseHelper(this);
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = objMyDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(ReminderPage.this, "The Database was empty", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                Log.d("ReadDatabase", data.getString(1));
                theList.add(data.getString(1));
                theList.add(data.getString(2));
                theList.add(data.getString(3));
            }
        }
        final ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
        listView.setAdapter(listAdapter);

}


    /**
     * opens the add reminder page
     * @param view
     */


    public void onClickAddReminder(View view) {
        Intent intent = new Intent(this, Add_Reminder.class);
        startActivity(intent);
    }



}
