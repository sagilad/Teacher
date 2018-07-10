package com.giladsagi.privateteacher;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class CityChoose extends Activity {

	public static String itemClicked = "בחר";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_choose);
		TextView Choose = (TextView) findViewById(R.id.textViewChoose);
		String sendActivity=getIntent().getExtras().getString("Activity");
		if (sendActivity.equals("City") || sendActivity.equals("City1"))
		{
			Choose.setText("אנא בחר אזור");
		}
		if (sendActivity.equals("Subject") || sendActivity.equals("Subject1"))
		{
			Choose.setText("אנא בחר תחום לימוד");
		}
		ExpandableListView ExCity;
		ExCity = (ExpandableListView) findViewById(R.id.expandableListCity);
		ExCity.setAdapter(new CityAdapter(this, sendActivity));
		if (sendActivity.equals("City") || sendActivity.equals("Subject"))
		{
			ExCity.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
					itemClicked =CityAdapter.parentList[groupPosition];
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result",itemClicked);
					setResult(RESULT_OK,returnIntent);
					finish();
					return false;
				}
			});
		}
		else if ((sendActivity.equals("City1") || sendActivity.equals("Subject1"))) {
			ExCity.setOnChildClickListener(new OnChildClickListener() {

				public boolean onChildClick(ExpandableListView parent, View v,
											int groupPosition, int childPosition, long id) {
					itemClicked = CityAdapter.childList[groupPosition][childPosition];
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result", itemClicked);
					setResult(RESULT_OK, returnIntent);
					finish();
					return false;
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_choose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
