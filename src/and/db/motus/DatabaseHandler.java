package and.db.motus;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Context
	private final Context MYCONTEXT;
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "motusManager";

	// Tables name
	private static final String TABLE_ALPHABET = "alphabet";
	private static final String TABLE_MOT = "mot";
	
	// Alphabet Table Columns names
	private static final String KEY_ALPHABET_ID = "alphabetid";
	private static final String KEY_ALPHABET_LETTRE = "alphabetlettre";
	

	// Mot Table Columns names
	private static final String KEY_MOT_ID = "motid";
	private static final String KEY_MOT_LETTRE = "motlettre";
	private static final String KEY_MOT_ALPHABET = "motalphabet";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		MYCONTEXT = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		/*String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_PH_NO + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);*/
		try {
			
	         InputStream is = MYCONTEXT.getResources().getAssets().open("install.sql");
	        
	         String[] statements = FileHelper.parseSqlFile(is);
	        
	         for (String statement : statements) {
	           db.execSQL(statement);
	         }
	     } catch (Exception ex) {
	       ex.printStackTrace();
	     }
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALPHABET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOT);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new alphabet
	public void addAlphabet(Alphabet alphabet) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ALPHABET_ID, alphabet.get_id());
		values.put(KEY_ALPHABET_LETTRE, alphabet.get_lettre());

		// Inserting Row
		db.insert(TABLE_ALPHABET, null, values);
		db.close(); // Closing database connection
	}

	// Adding new mot
	public void addMot(Mot mot) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_MOT_ID, mot.get_id());
		values.put(KEY_MOT_LETTRE, mot.get_lettre());
		values.put(KEY_MOT_ALPHABET, mot.get_alphabetid());

		// Inserting Row
		db.insert(TABLE_MOT, null, values);
		db.close(); // Closing database connection
	}
	
	// Getting single alphabet
	public Alphabet getAlphabet(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ALPHABET, new String[] { KEY_ALPHABET_ID,
				KEY_ALPHABET_LETTRE }, KEY_ALPHABET_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Alphabet alphabet = new Alphabet(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		// return contact
		return alphabet;
	}
	
	// Getting single mot
	public Mot getMot(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MOT, new String[] { KEY_MOT_ID,
				KEY_MOT_LETTRE, KEY_MOT_ALPHABET }, KEY_MOT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Mot mot = new Mot(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)));
		// return contact
		return mot;
	}

	
	// Getting All Alphabet
	public List<Alphabet> getAllAlphabets() {
		List<Alphabet> alphabetList = new ArrayList<Alphabet>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ALPHABET;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Alphabet alphabet = new Alphabet();
				alphabet.set_id(Integer.parseInt(cursor.getString(0)));
				alphabet.set_lettre(cursor.getString(1));
				
				// Adding alphabet to list
				alphabetList.add(alphabet);
			} while (cursor.moveToNext());
		}

		// return contact list
		return alphabetList;
	}

	// Getting All Mot
	public List<Mot> getAllMots() {
		List<Mot> motList = new ArrayList<Mot>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MOT;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Mot mot = new Mot();
				mot.set_id(Integer.parseInt(cursor.getString(0)));
				mot.set_lettre(cursor.getString(1));
				mot.set_lettre(cursor.getString(2));
				
				// Adding alphabet to list
				motList.add(mot);
			} while (cursor.moveToNext());
		}

		// return contact list
		return motList;
	}
	
	// Updating single contact
	/*public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName());
		values.put(KEY_PH_NO, contact.getPhoneNumber());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}
	*/

	// Getting alphabet Count
	public int getAlphabetCount() {
		String countQuery = "SELECT  * FROM " + TABLE_ALPHABET;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	
	// Getting mot Count
	public int getMotCount() {
		String countQuery = "SELECT  * FROM " + TABLE_MOT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
