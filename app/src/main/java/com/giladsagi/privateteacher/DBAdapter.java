// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.giladsagi.privateteacher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.


public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_FULLNAME = "fullname";
	public static final String KEY_CITY = "city";
	public static final String KEY_SUBJECT = "subject";
	public static final String KEY_INFO = "info";
	public static final String KEY_AGE = "age";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_PRICE = "price";
	public static final String KEY_RATING = "rating";
	public static final String KEY_TELEPHONE = "telephone";
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_USERNAME = 1;
	public static final int COL_PASSWORD = 2;
	public static final int COL_FULLNAME = 3;
	public static final int COL_CITY = 4;
	public static final int COL_SUBJECT = 5;
	public static final int COL_INFO = 6;
	public static final int COL_AGE = 7;
	public static final int COL_GENDER = 8;
	public static final int COL_PRICE = 9;
	public static final int COL_RATING = 10;
	public static final int COL_TELEPHONE = 11;

	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_USERNAME, KEY_PASSWORD, KEY_FULLNAME, KEY_CITY, KEY_SUBJECT, KEY_INFO, KEY_AGE, KEY_GENDER, KEY_PRICE, KEY_RATING, KEY_TELEPHONE};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 3;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ KEY_USERNAME + " text not null, "
			+ KEY_PASSWORD + " string not null, "
			+ KEY_FULLNAME + " text not null,"
			+ KEY_CITY + " text not null,"
			+ KEY_SUBJECT + " string not null,"
			+ KEY_INFO + " text not null,"
			+ KEY_AGE + " integer not null,"
			+ KEY_GENDER + " text not null,"
			+ KEY_PRICE + " integer not null,"
			+ KEY_RATING + " integer not null,"
			+ KEY_TELEPHONE + " String not null"
			
			// Rest  of creation:
			+ ");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(String username, String password, String fullname, String city, String subject, String info, int age, String gender, int price, int rating, String telephone) {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_PASSWORD, password);
		initialValues.put(KEY_FULLNAME, fullname);
		initialValues.put(KEY_CITY, city);
		initialValues.put(KEY_SUBJECT, subject);
		initialValues.put(KEY_INFO, info);
		initialValues.put(KEY_AGE, age);
		initialValues.put(KEY_GENDER, gender);
		initialValues.put(KEY_PRICE, price);
		initialValues.put(KEY_RATING, rating);
		initialValues.put(KEY_TELEPHONE, telephone);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All cities)
	public Cursor getRowAllCity(String subject) {
		String where = KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All subjects)
	public Cursor getRowAllSubject(String city) {
		String where = KEY_CITY + "='" + city + "'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get rows (by city and subject)
	public Cursor getRow(String city, String subject) {
		String where = KEY_CITY + "='" + city + "'" +" AND " + KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (by city and subject sorted by price)
	public Cursor getRowByPrice(String city, String subject) {
		String where = KEY_CITY + "='" + city + "'" +" AND " + KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_PRICE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All cities sorted by price)
	public Cursor getRowByPriceAllCity(String subject) {
		String where = KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_PRICE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All subjects sorted by price)
	public Cursor getRowByPriceAllSubject(String city) {
		String where = KEY_CITY + "='" + city + "'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_PRICE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Return all data in the database sorted by price.
	public Cursor getAllRowsByPrice() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, KEY_PRICE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	// Get rows (by city and subject sorted by Rating)
	public Cursor getRowByRating(String city, String subject) {
		String where = KEY_CITY + "='" + city + "'" +" AND " + KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_RATING, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All cities sorted by Rating)
	public Cursor getRowByRatingAllCity(String subject) {
		String where = KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_RATING, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All subjects sorted by Rating)
	public Cursor getRowByRatingAllSubject(String city) {
		String where = KEY_CITY + "='" + city + "'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_RATING, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Return all data in the database sorted by Rating.
	public Cursor getAllRowsByRating() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, KEY_RATING, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (by city and subject sorted by Age)
	public Cursor getRowByAge(String city, String subject) {
		String where = KEY_CITY + "='" + city + "'" +" AND " + KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_AGE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All cities sorted by Age)
	public Cursor getRowByAgeAllCity(String subject) {
		String where = KEY_SUBJECT + "='" + subject +"'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_AGE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get rows (All subjects sorted by Age)
	public Cursor getRowByAgeAllSubject(String city) {
		String where = KEY_CITY + "='" + city + "'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, KEY_AGE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Return all data in the database sorted by Age.
	public Cursor getAllRowsByAge() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, KEY_AGE, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get row (by rowID)
	public Cursor getRowID(long rowID) {
		String where = KEY_ROWID + "='" + rowID + "'";
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String username, String password, String fullname, String city, String subject, String info, int age, String gender, int price, int rating, String telephone) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_USERNAME, username);
		newValues.put(KEY_PASSWORD, password);
		newValues.put(KEY_FULLNAME, fullname);
		newValues.put(KEY_CITY, city);
		newValues.put(KEY_SUBJECT, subject);
		newValues.put(KEY_INFO, info);
		newValues.put(KEY_AGE, age);
		newValues.put(KEY_GENDER, gender);
		newValues.put(KEY_PRICE, price);
		newValues.put(KEY_RATING, rating);
		newValues.put(KEY_TELEPHONE, telephone);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
