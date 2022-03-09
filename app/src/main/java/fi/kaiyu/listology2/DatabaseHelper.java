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
    private final String queryToCreateDatabase = "create table Reminders (ID INTEGER PRIMARY KEY AUTOINCREMENT" + ",EventName VARCHAR(255),Date VARCHAR(255),Time VARCHAR(255))";
    Context context;
    private SQLiteDatabase db;


    /**
     * Creates a database
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    /**
     * checks if the database is created or not
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(queryToCreateDatabase);
            Toast.makeText(context, "Table is created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error while creating table", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *
     * @param db -database object
     * @param oldVersion - oldVersion of the database
     * @param newVersion -newVersion of the database
     * @see <a href="https://www.youtube.com/watch?v=e3fLWNEBPM0&list=PLzEWSvaHx_Z2MeyGNQeUCEktmnJBp8136&index=4">Tutorial (see lines 28-34 in the video)</a>
     */

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

    /**
     *
     * @param id -unique id of the task
     * @param task -the string of the task
     * @see <a href="https://www.youtube.com/watch?v=e3fLWNEBPM0&list=PLzEWSvaHx_Z2MeyGNQeUCEktmnJBp8136&index=4">Tutorial (see lines 85-89 in the video)</a>
     */
    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TABLE_NAME, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    /**
     *
     * @param id -unique id of the task
     * @see <a href="https://www.youtube.com/watch?v=e3fLWNEBPM0&list=PLzEWSvaHx_Z2MeyGNQeUCEktmnJBp8136&index=4">Tutorial (see lines 91-93 in the video)</a>
     */
    public void deleteTask(int id){
        db.delete(TABLE_NAME, ID + "= ?", new String[] {String.valueOf(id)});
    }


}


