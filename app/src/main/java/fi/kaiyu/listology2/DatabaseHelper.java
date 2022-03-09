package fi.kaiyu.listology2;

import android.content.ContentValues;
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
    private static final String ID = "ID";
    private static final String TASK = "task";
    private static final String COL3 = "Date";
    private static final String COL4 = "Time";
    private final String queryToCreateDatabase = "create table Reminders (ID INTEGER PRIMARY KEY AUTOINCREMENT" + ",EventName VARCHAR(255),Date VARCHAR(255),Time VARCHAR(255))";
    Context context;
    private SQLiteDatabase db;

    public static String getID() {
        return ID;
    }

    public static String getTASK() {
        return TASK;
    }

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
            db.execSQL(queryToCreateDatabase);
            Toast.makeText(context, "Table is created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error while creating table", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }


    /**
     * returns the data from a database
     * @return data
     */
    public Cursor getListContents() {
        SQLiteDatabase objSQLiteDatabase = this.getWritableDatabase();
        Cursor data = objSQLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return data;

    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TABLE_NAME, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TABLE_NAME, ID + "= ?", new String[] {String.valueOf(id)});
    }


}


