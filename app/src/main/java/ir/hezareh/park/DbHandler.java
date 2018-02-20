package ir.hezareh.park;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.models.ModelComponent;


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
    Boolean addVote(int ID, int Vote) {
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

    // Getting All Contacts
    public List<ModelComponent> getAllNews(int index) {
        ArrayList<ModelComponent> components = new ArrayList<>();

        ModelComponent modelComponent = new ModelComponent();
        ModelComponent.Item newsItem = modelComponent.new Item();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NEWSLIST + " WHERE index=" + String.valueOf(index);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                newsItem.setID(Integer.parseInt(cursor.getString(0)));
                newsItem.setImage(cursor.getString(2));
                newsItem.setText(cursor.getString(3));
                newsItem.setDate(cursor.getString(4));
                newsItem.setContent(cursor.getString(5));
                newsItem.setUrl(cursor.getString(6));
                newsItem.setLikes(cursor.getString(7));
                newsItem.setDislikes(cursor.getString(8));
                newsItem.setComment(cursor.getString(9));

                components.add(modelComponent);

            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return components;
    }

}
