package com.hmkcode.android.sqlite;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MotDB";
   
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		String CREATE_MOT_TABLE = "CREATE TABLE mots ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				"title TEXT, "+
				"author TEXT )";
		
		// create books table
		db.execSQL(CREATE_MOT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS mots");
        
        // create fresh books table
        this.onCreate(db);
	}
	//---------------------------------------------------------------------
   
	/**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
	
	// Books table name
    private static final String TABLE_MOTS = "mots";
    
    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    
    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_AUTHOR};
    
	public void addMot(MotDb mot){
		Log.d("addMot", mot.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, mot.getTitle()); // get title 
        values.put(KEY_AUTHOR, mot.getAuthor()); // get author
 
        // 3. insert
        db.insert(TABLE_MOTS, // table
        		null, //nullColumnHack
        		values); // key/value -> keys = column names/ values = column values
        
        // 4. close
        db.close(); 
	}
	
	public MotDb getMot(int id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		 
		// 2. build query
        Cursor cursor = 
        		db.query(TABLE_MOTS, // a. table
        		COLUMNS, // b. column names
        		" id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        MotDb mot = new MotDb();
        mot.setId(Integer.parseInt(cursor.getString(0)));
        mot.setTitle(cursor.getString(1));
        mot.setAuthor(cursor.getString(2));

		Log.d("getMot("+id+")", mot.toString());

        // 5. return book
        return mot;
	}
	
	// Get All Books
    public List<MotDb> getAllMots() {
        List<MotDb> mots = new LinkedList<MotDb>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MOTS;
 
    	// 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        MotDb mot = null;
        if (cursor.moveToFirst()) {
            do {
            	mot = new MotDb();
                mot.setId(Integer.parseInt(cursor.getString(0)));
                mot.setTitle(cursor.getString(1));
                mot.setAuthor(cursor.getString(2));

                // Add book to books
                mots.add(mot);
            } while (cursor.moveToNext());
        }
        
		Log.d("getAllMots()", mots.toString());

        // return books
        return mots;
    }
	
	 // Updating single book
    public int updateMot(MotDb mot) {

    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", mot.getTitle()); // get title 
        values.put("author", mot.getAuthor()); // get author
 
        // 3. updating row
        int i = db.update(TABLE_MOTS, //table
        		values, // column/value
        		KEY_ID+" = ?", // selections
                new String[] { String.valueOf(mot.getId()) }); //selection args
        
        // 4. close
        db.close();
        
        return i;
        
    }

    // Deleting single book
    public void deleteMot(MotDb mot) {

    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        // 2. delete
        db.delete(TABLE_MOTS,
        		KEY_ID+" = ?",
                new String[] { String.valueOf(mot.getId()) });
        
        // 3. close
        db.close();
        
		Log.d("deleteMot", mot.toString());

    }
}
