package com.example.fixme;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * creates a Database and helps in handling it
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RemindersInfo.db";
    private static final String TABLE_NAME = "Reminders";
    private static final String COL1 = "ID";
    private static final String COL2 = "EventName";
    private static final String COL3 = "Date";
    private static final String COL4 = "Time";
    private String queryToCreatDatabase = "create table Reminders (ID INTEGER PRIMARY KEY AUTOINCREMENT" + ",EventName VARCHAR(255),Date VARCHAR(255),Time VARCHAR(255))";
    Context context;

    /**
     * Creates a database
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    /**
     * checks if the database is created or not
     */
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(queryToCreatDatabase);
            Toast.makeText(context, "Table is created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error while creating table", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * updates the database but we couldnt implement it due to lack of time :(
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * returns the data from a database
     * @return
     */
    public Cursor getListContents() {
        SQLiteDatabase objSQLiteDatabase = this.getWritableDatabase();
        Cursor data = objSQLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return data;

    }


}


