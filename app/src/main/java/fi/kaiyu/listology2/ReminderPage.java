package fi.kaiyu.listology2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Shows all the data in the database and uses a button to open Add_Reminder activity
 */

public class ReminderPage extends AppCompatActivity {
    /**
     * The database object for storing the information in the database
     */
    DatabaseHelper objMyDB;

    /**
     * Defining the button for adding task
     */
    Button btnAddTask;

    /**
     * Displays the items saved in the database to listview
     * @param savedInstanceState -a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_page);

        final ListView listView = findViewById(R.id.reminder_list);

        objMyDB = new DatabaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();

        //getting all the data from the database
        Cursor data = objMyDB.getListContents();

        if (data.getCount() == 0) {
            Log.d("Database: ", "The database was empty");
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));     //add the task
                theList.add(data.getString(2));     //add the date
                theList.add(data.getString(3));     //add the time
            }
        }

        //defining a ListAdapter
        final ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
        listView.setAdapter(listAdapter);

        btnAddTask = findViewById(R.id.button);

        //open activity when the button is clicked
        btnAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(this, Add_Reminder.class);
            startActivity(intent);
        });
    }


}
