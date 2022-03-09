package fi.kaiyu.listology2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;



import java.util.ArrayList;

/**
 * Shows all the data in the database and uses a button to open Add_Task activity
 */

public class ReminderPage extends AppCompatActivity {
    /**
     * The database object for storing the information in the database
     */
    DatabaseHelper objMyDB;

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
                Log.d("ReadDatabase", data.getString(1));
                theList.add(data.getString(1));
                theList.add(data.getString(2));
                theList.add(data.getString(3));
            }
        }
        final ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent updateTaskIntent = new Intent(this, UpdateTask.class);


            //Getting the information of the task that the user has clicked
            String task = DatabaseHelper.getTASK();
            String id = DatabaseHelper.getID();

            //Putting all the information in the bundle
            Bundle bundle = new Bundle();
            bundle.putString("TASK", task);
            bundle.putString("ID", id);


            //Now putting the bundle itself as Extras when starting the new activity
            updateTaskIntent.putExtras(bundle);
            startActivity(updateTaskIntent);
        });

}

    /**
     * opens the add Task page
     * @param view -the button which will take us to the Add Tasks page
     */
    public void onClickAddReminder(View view) {
        Intent intent = new Intent(this, Add_Reminder.class);
        startActivity(intent);
    }



}
