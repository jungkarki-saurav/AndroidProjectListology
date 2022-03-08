package com.example.fixme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * combines the reminder page and calendar page
 */
public class MainActivity extends AppCompatActivity {
    /**
     * the main activity page
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Shows the calendar page.
     * @param view
     */
    public void onClickCalendar(View view) {
        Intent intent = new Intent(this, CalendarPage.class);
        startActivity(intent);
    }

    /**
     * Shows the reminder page
     * @param view
     */
    public void onClickReminder(View view) {
        Intent intent = new Intent(this, ReminderPage.class);
        startActivity(intent);
    }
}
