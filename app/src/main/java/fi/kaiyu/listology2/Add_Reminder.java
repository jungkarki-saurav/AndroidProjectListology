package fi.kaiyu.listology2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/**
 * Allows the user to write task, select date and time, and then store it to database after clicking 'save' button
 */
public class Add_Reminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddReminder";
    private EditText eventText;
    private TextView dateText;
    private TextView timeText;
    private final Context time = this;

    /**
     * The database object for storing the information in the database
     */
    DatabaseHelper objMyDB;

    /**
     * Allows the user to pick date and time
     * @param savedInstanceState -a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__reminder);

        Log.d(TAG, "onCreate: ");

        objMyDB = new DatabaseHelper(this);

        eventText = findViewById(R.id.eventName);
        dateText = findViewById(R.id.showDate);
        timeText =  findViewById(R.id.showTime);
        Button timePicker = findViewById(R.id.pickTime);

        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        //enter date by clicking the 'pick a date' button
        findViewById(R.id.pickDate).setOnClickListener(view -> showDatePickerDialog());

        //enter date by clicking the 'pick a time' button
        timePicker.setOnClickListener(v -> {
            @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(time, (view, hourOfDay, minute1) -> timeText.setText(hourOfDay + ":" + minute1), hour, minute, android.text.format.DateFormat.is24HourFormat(time));
            timePickerDialog.show();
        });

    }

    /**
     * shows the date picker dialog
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Displays the date picker on textview
     * @param view
     * @param year -the year entered by the user
     * @param month -the month entered by the user
     * @param dayOfMonth -the dayOfMonth entered by the user
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }


    /**
     * adds items to the database
     * @param view
     */
    public void insertIntoDatabase(View view) {
        try {
            SQLiteDatabase objSQLiteDatabase = objMyDB.getWritableDatabase();       //open database in writing mode

            //check if database exists
            if (objSQLiteDatabase != null) {

                //Check if all the fields are filled
                if (!eventText.getText().toString().isEmpty() && !dateText.getText().toString().isEmpty() && !timeText.getText().toString().isEmpty()) {
                    ContentValues objContentValues = new ContentValues();
                    //put the values entered by the user (task, date, time) in the object 'objContentValues'
                    objContentValues.put("EventName", eventText.getText().toString());
                    objContentValues.put("Date", dateText.getText().toString());
                    objContentValues.put("Time", timeText.getText().toString());

                    //insert data into database
                    long checkIfQueryRuns = objSQLiteDatabase.insert("Reminders", null, objContentValues);

                    //check if the data is added to the database
                    if (checkIfQueryRuns != -1) {
                        Toast.makeText(this, "Reminder Saved :D ", Toast.LENGTH_SHORT).show();
                        eventText.setText(null);
                        dateText.setText(null);
                        timeText.setText(null);

                        //new intent go back to list view...
                        Intent intent =  new Intent(this,ReminderPage.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "Problem in saving reminder :( ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Database is null :(", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "insert values into Database:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}

