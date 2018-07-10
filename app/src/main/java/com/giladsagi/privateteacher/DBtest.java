package com.giladsagi.privateteacher;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DBtest extends ActionBarActivity {

	//ListView lstTeachers;
	JSONAdapter jSONAdapter;
	int sort=0;
	public static String city;
	public static String subject;
	MyProgressDialog pd;
	public static String GetCity()
	{
		return city;
	}
	
	public static String GetSubject()
	{
		return subject;
	}

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;

	private List<ListItem> listItems;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbtest);
		pd = new MyProgressDialog(DBtest.this);
		pd.setMessage("טוען...");
		pd.show();
		city = getIntent().getExtras().getString("City");
		subject = getIntent().getExtras().getString("subject");
		recyclerView = (RecyclerView)findViewById(R.id.lstTeachers);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		listItems = new ArrayList<>();

		new GetAllTeachers().execute(new ApiConnector());
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
	
	private void setTextToTextView(JSONArray jsonArray) 
	{
	//	jSONAdapter = new JSONAdapter (DBtest.this, jsonArray);
	//	lstTeachers.setAdapter(jSONAdapter);

		listItems.clear();

        for (int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject o = jsonArray.getJSONObject(i);
                ListItem item = new ListItem(
                		o.getString("teacherID"),
						o.getString("city"),
						o.getString("subject"),
						o.getString("telephone"),
						o.getString("info"),
						o.getString("email"),
                        o.getString("fullname"),
                        o.getString("age"),
                        o.getString("gender"),
                        o.getString("price"),
                        o.getString("rating_avg"),
                        o.getString("ImageName")
                );
                listItems.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

		adapter = new MyAdapter(listItems, getApplicationContext());

		recyclerView.setAdapter(adapter);
	}
	
	private class GetAllTeachers extends AsyncTask<ApiConnector, Long, JSONArray>
	{

		@Override
		protected JSONArray doInBackground(ApiConnector... params) {
			if (sort==1)
				return params[0].GetTeachersByPrice();
			if (sort==2)
				return params[0].GetTeachersByRating();
			if (sort==3)
				return params[0].GetTeachersByAge();
			else
				return params[0].GetAllTeachers();
		}
		
		@Override
		protected void onPostExecute(JSONArray jsonArray)
		{
			if (pd.isShowing())
				pd.dismiss();
            if (jsonArray != null)
			    setTextToTextView(jsonArray);
			else
				Toast.makeText(getApplicationContext(), "לא נמצאו מורים", Toast.LENGTH_LONG).show();
		} 
	}
	
	public void SortByPrice(View v)
	{
		final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
		v.startAnimation(buttonClick);
		sort=1;
		new GetAllTeachers().execute(new ApiConnector());
	}
	
	public void SortByRating(View v)
	{
		final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
		v.startAnimation(buttonClick);
		sort=2;
		new GetAllTeachers().execute(new ApiConnector());
	}
	
	public void SortByAge(View v)
	{
		final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
		v.startAnimation(buttonClick);
		sort=3;
		new GetAllTeachers().execute(new ApiConnector());
	}

}
