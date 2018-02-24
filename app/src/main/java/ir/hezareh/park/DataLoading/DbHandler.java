package ir.hezareh.park.DataLoading;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHandler extends SQLiteOpenHelper {

    public static final String TAG = DbHandler.class
            .getSimpleName();
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "OfflineDataManager";

    // Contacts table name
    private static final String TABLE_NEWSLIST = "newsList";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_VOTED = "voted";

    public DbHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NEWSLIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_VOTED + " INTEGER )";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSLIST);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public Boolean addVote(int ID, int Vote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, ID);
        values.put(KEY_VOTED, Vote);

        // Inserting Row
        try {
            db.insertOrThrow(TABLE_NEWSLIST, null, values);
            db.close(); // Closing database connection
            return true;
        } catch (SQLiteConstraintException e) {
            Log.d(TAG, e.getMessage());
            db.close(); // Closing database connection
            return false;
        }

    }

    // Getting single contact
    int getVote(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWSLIST, new String[]{
                        KEY_VOTED}, KEY_ID + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.close();
        }
        return cursor.getInt(2);
    }


}
