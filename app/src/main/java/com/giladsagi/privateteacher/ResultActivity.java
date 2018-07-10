package com.giladsagi.privateteacher;



import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


  public class ResultActivity extends Activity {
	
	DBAdapter MyDb;
	String city;
	String subject;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		getDB();
		TextView subjectView=(TextView) findViewById(R.id.text_subject);
		subjectView.setText(subject);
		TextView cityView=(TextView) findViewById(R.id.text_city);
		cityView.setText(city);
		registerListClockCallback();
	}
	

	public void getDB()
	{
		openDB();
		city = getIntent().getExtras().getString("City");
		subject = getIntent().getExtras().getString("subject");
		Cursor cursor = null;
		if (city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getAllRows();
		}
		
		else if (city.equals("All")  && !subject.equals("All") )
		{
			cursor = MyDb.getRowAllCity(subject);
		}
		
		else if (!city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getRowAllSubject(city);
		}
		
		else
		{
			cursor = MyDb.getRow(city, subject);
		}
		startManagingCursor(cursor);
		displayRecordSet(cursor);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}


	private void openDB() {
		MyDb = new DBAdapter(this);
		MyDb.open();
	}
	private void closeDB() {
		MyDb.close();
	}
	
	private void displayRecordSet(Cursor cursor) {
		// Reset cursor to start, checking to see if there's data:
				String [] fromFieldNames = new String[] {DBAdapter.KEY_FULLNAME, DBAdapter.KEY_AGE, DBAdapter.KEY_GENDER, DBAdapter.KEY_PRICE, DBAdapter.KEY_RATING};
				int[] toViewIDs = new int[] {R.id.teacher_name, R.id.teacher_price, R.id.teacher_raitings};
				
				SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.result_layout, cursor, fromFieldNames, toViewIDs);
				ListView lv = (ListView) findViewById(R.id.listViewSujects);
				lv.setAdapter(myCursorAdapter);
//				 if ("0".equals(String.valueOf(cursor.getInt(DBAdapter.COL_RATING))))
//				 {
//				 }
				 

		}
		
	private void registerListClockCallback() {
		ListView lv = (ListView) findViewById(R.id.listViewSujects);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long idInDB) {
				
		    	Intent i = new Intent(ResultActivity.this,TecherDetails.class);
		    	Cursor cursor = MyDb.getRowID(idInDB);
		    	if (cursor.moveToFirst()){
		    		long selectedID = cursor.getLong(DBAdapter.COL_ROWID);
					String selectedName = cursor.getString(DBAdapter.COL_FULLNAME);
					String selectedCity = cursor.getString(DBAdapter.COL_CITY);
					String selectedSub = cursor.getString(DBAdapter.COL_SUBJECT);
					String selectedInfo = cursor.getString(DBAdapter.COL_INFO);
					String selectedAge = String.valueOf(cursor.getInt(DBAdapter.COL_AGE));
					String selectedGender = cursor.getString(DBAdapter.COL_GENDER);
					String selectedPrice = String.valueOf(cursor.getInt(DBAdapter.COL_PRICE));
					String selectedRaiting = String.valueOf(cursor.getInt(DBAdapter.COL_RATING));
					String selectedTel = cursor.getString(DBAdapter.COL_TELEPHONE);
	    	
		    	i.putExtra("ID", selectedID);
		    	i.putExtra("Name", selectedName);
		    	i.putExtra("City", selectedCity );
		    	i.putExtra("Subject", selectedSub );
		    	i.putExtra("Info", selectedInfo );
		    	i.putExtra("Age", selectedAge );
		    	i.putExtra("Gender", selectedGender );
		    	i.putExtra("Price", selectedPrice );
		    	i.putExtra("Rating", selectedRaiting );
		    	i.putExtra("Telephone", selectedTel );
		    	}
		    	cursor.close();
		    	startActivity(i);
			}
		});
		
	}	
	
	public void SortByPrice(View v)
	{
		Cursor cursor = null;
		if (city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getAllRowsByPrice();
		}
		
		else if (city.equals("All")  && !subject.equals("All") )
		{
			cursor = MyDb.getRowByPriceAllCity(subject);
		}
		
		else if (!city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getRowByPriceAllSubject(city);
		}
		
		else
		{
			cursor = MyDb.getRowByPrice(city, subject);
		}
		startManagingCursor(cursor);
		displayRecordSet(cursor);
		
		
	}
	
	public void SortByRating(View v)
	{
		Cursor cursor = null;
		if (city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getAllRowsByRating();
		}
		
		else if (city.equals("All")  && !subject.equals("All") )
		{
			cursor = MyDb.getRowByRatingAllCity(subject);
		}
		
		else if (!city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getRowByRatingAllSubject(city);
		}
		
		else
		{
			cursor = MyDb.getRowByRating(city, subject);
		}
		startManagingCursor(cursor);
		displayRecordSet(cursor);
	}
	
	public void SortByAge(View v)
	{
		Cursor cursor = null;
		if (city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getAllRowsByAge();
		}
		
		else if (city.equals("All")  && !subject.equals("All") )
		{
			cursor = MyDb.getRowByAgeAllCity(subject);
		}
		
		else if (!city.equals("All")  && subject.equals("All"))
		{
			cursor = MyDb.getRowByAgeAllSubject(city);
		}
		
		else
		{
			cursor = MyDb.getRowByAge(city, subject);
		}
		startManagingCursor(cursor);
		displayRecordSet(cursor);
	}
}
