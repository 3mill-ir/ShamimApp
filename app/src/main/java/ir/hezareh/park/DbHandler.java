package ir.hezareh.park;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ir.hezareh.park.models.ModelComponent;


public class DbHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "OfflineDataManager";

    // Contacts table name
    private static final String TABLE_NEWSLIST = "newsList";

    // Contacts Table Columns names
    private static final String KEY_INDEX = "index";
    private static final String KEY_ID = "id";
    private static final String KEY_ImagePath = "ImagePath";
    private static final String KEY_TEXT = "Text";
    private static final String KEY_Date = "Date";
    private static final String KEY_Content = "Content";
    private static final String KEY_Url = "Url";
    private static final String KEY_Likes = "Likes";
    private static final String KEY_Dislikes = "Dislikes";
    private static final String KEY_Comment = "Comment";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NEWSLIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_INDEX + "INTEGER"
                + KEY_ImagePath + " TEXT,"
                + KEY_TEXT + " TEXT,"
                + KEY_Date + " TEXT,"
                + KEY_Content + " TEXT,"
                + KEY_Url + " TEXT,"
                + KEY_Likes + " INTEGER,"
                + KEY_Dislikes + " INTEGER,"
                + KEY_Comment + " INTEGER" + ")";
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
    void addNews(ModelComponent.Item news, int index) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, news.getID());
        values.put(KEY_INDEX, index);
        values.put(KEY_ImagePath, news.getImage().toString());
        values.put(KEY_TEXT, news.getText());
        values.put(KEY_Date, news.getDate().toString());
        values.put(KEY_Content, news.getContent().toString());
        values.put(KEY_Url, news.getUrl());
        values.put(KEY_Likes, news.getLikes());
        values.put(KEY_Dislikes, news.getDislikes());
        values.put(KEY_Comment, news.getComment());

        // Inserting Row
        db.insertWithOnConflict(TABLE_NEWSLIST, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
    }

    /*// Getting single contact
    ModelComponent.Item getNews(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWSLIST, new String[] { KEY_ID,
                        KEY_ImagePath, KEY_TEXT,KEY_Date,KEY_Content
                ,KEY_Url,KEY_Likes,KEY_Dislikes,KEY_Comment}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ModelComponent.Item contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }*/

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

    // Updating single contact
    public int updateContact(ModelComponent.Item newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, newsItem.getID());
        values.put(KEY_ImagePath, newsItem.getImage().toString());
        values.put(KEY_TEXT, newsItem.getText());
        values.put(KEY_Date, newsItem.getDate().toString());
        values.put(KEY_Content, newsItem.getContent().toString());
        values.put(KEY_Url, newsItem.getUrl());
        values.put(KEY_Likes, newsItem.getLikes());
        values.put(KEY_Dislikes, newsItem.getDislikes());
        values.put(KEY_Comment, newsItem.getComment());

        // updating row
        return db.update(TABLE_NEWSLIST, values, KEY_ID + " = ?",
                new String[]{String.valueOf(newsItem.getID())});
    }

    public int updatePath(int ID, String Path) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, ID);
        values.put(KEY_ImagePath, Path);

        // updating row
        return db.update(TABLE_NEWSLIST, values, KEY_ID + " = ?",
                new String[]{String.valueOf(ID)});
    }


    // Deleting single contact
    public void deleteContact(ModelComponent.Item newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWSLIST, KEY_ID + " = ?",
                new String[]{String.valueOf(newsItem.getID())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NEWSLIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


}
