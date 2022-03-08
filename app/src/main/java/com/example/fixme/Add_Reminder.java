package com.example.fixme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 *pop up date picker dialog, pop up time picker dialog and saves reminders into database
 */
public class Add_Reminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button saveButton;
    private EditText eventText;
    private TextView dateText;
    private TextView timeText;
    private Button timePicker;
    private Context time = this;
    DatabaseHelper objMyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__reminder);
        Intent intent = getIntent();
        objMyDB = new DatabaseHelper(this);
        eventText = (EditText) findViewById(R.id.eventName);
        saveButton = (Button) findViewById(R.id.saveButton);
        dateText = findViewById(R.id.showDate);
        timeText = (TextView) findViewById(R.id.showTime);
        timePicker = (Button) findViewById(R.id.pickTime);
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        findViewById(R.id.pickDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(time, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeText.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(time));
                timePickerDialog.show();
            }
        });

    }

    /**
     * show the pop up date picker dialog
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
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }

    /**
     * creates a database when reminder page opened
     * @param view
     */
    public void createDatabase(View view) {
        try {
            objMyDB.getReadableDatabase();
        } catch (Exception e) {
            Toast.makeText(this, "exception while creating database" + e.getMessage(), Toast.LENGTH_SHORT);

        }

    }

    /**
     * adds items to the database
     * @param view
     */
    public void insertIntoDatabase(View view) {
        try {
            SQLiteDatabase objSQLiteDatabase = objMyDB.getWritableDatabase();
            if (objSQLiteDatabase != null) {
                if (!eventText.getText().toString().isEmpty() && !dateText.getText().toString().isEmpty() && !timeText.getText().toString().isEmpty()
                ) {
                    ContentValues objContentValues = new ContentValues();
                    objContentValues.put("EventName", eventText.getText().toString());
                    objContentValues.put("Date", dateText.getText().toString());
                    objContentValues.put("Time", timeText.getText().toString());

                    long checkIfQueryRuns = objSQLiteDatabase.insert("Reminders", null, objContentValues);
                    if (checkIfQueryRuns != -1) {
                        Toast.makeText(this, "Reminder Saved :D ", Toast.LENGTH_SHORT).show();
                        eventText.setText(null);
                        dateText.setText(null);
                        timeText.setText(null);
                        Intent intent =  new Intent(this,ReminderPage.class);
                        startActivity(intent);
                        //new intent go back to list view...
                    } else {
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

