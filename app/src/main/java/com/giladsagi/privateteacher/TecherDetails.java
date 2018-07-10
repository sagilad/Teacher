package com.giladsagi.privateteacher;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class TecherDetails extends ActionBarActivity {
	
	private static final String baseUrlForImage = ApiConnector.website + "images/";
	public static String phoneNumber="", emailSubject, emailBody, teacherID;
	private ImageButton buttonEmail, buttonRating;
	final Context context = this;
	EditText editSubject, editBody;
	public static RatingBar barPro, barVibility, barTeach, barGeneral;
	TextView scorePro, scoreAvil, scoreTeach, scoreGeneral;
	DecimalFormat df;
	String android_id = MainActivity.android_id;
	boolean teacher_rated = false;
	
	MyProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_techer_details);
		teacherID = getIntent().getExtras().getString("TeacherID");
		TextView NameView=(TextView) findViewById(R.id.teacher_name);
		TextView AgeView=(TextView) findViewById(R.id.t_age);
		TextView GenderView=(TextView) findViewById(R.id.t_gender);
        TextView cityView = (TextView)findViewById(R.id.citieslist);
        TextView subjectView = (TextView)findViewById(R.id.subjectslist);
		TextView PriceView=(TextView) findViewById(R.id.t_price);
		TextView RatingView=(TextView) findViewById(R.id.t_rating);
		TextView TelView=(TextView) findViewById(R.id.t_tel);
		TextView InfoView=(TextView) findViewById(R.id.t_info);
		ImageView TeacherImage =(ImageView) findViewById(R.id.imageViewTeacher);
		showImage(TeacherImage);
		NameView.setText(getIntent().getExtras().getString("Name"));
		GenderView.setText(getIntent().getExtras().getString("Gender"));
		AgeView.setText(getIntent().getExtras().getString("Age"));
		PriceView.setText(getIntent().getExtras().getString("Price"));
		if ( "null".equals(getIntent().getExtras().getString("Rating")))
		{
			RatingView.setText("לא דורג");
		}
		else
		{
			if (getIntent().getExtras().getString("Rating").length()>3)
			{
				RatingView.setText(getIntent().getExtras().getString("Rating").substring(0, 3));
			}
			else 
			{
				RatingView.setText(getIntent().getExtras().getString("Rating"));
			}
		}
		
		TelView.setText(getIntent().getExtras().getString("Telephone"));
		phoneNumber=getIntent().getExtras().getString("Telephone");
		InfoView.setText(getIntent().getExtras().getString("Info"));
		emailMethod();
		df = new DecimalFormat();
		df.setMaximumFractionDigits(1);
		
		ratingMethod();

		String cities = getIntent().getExtras().getString("City");
        String subjects = getIntent().getExtras().getString("Subject");
        cities = cities.replaceAll(",", "\n");
        subjects = subjects.replaceAll(",", "\n");
        cityView.setText(cities);
        subjectView.setText(subjects);

	}
	
	public void emailMethod()
	{
		buttonEmail = (ImageButton) findViewById(R.id.emailbutton);
		buttonEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.sendemail);
				Drawable d = new ColorDrawable(Color.BLACK);
				d.setAlpha(130);
				dialog.getWindow().setBackgroundDrawable(d);
				//dialog.setTitle("Email Message");
				editSubject = (EditText) dialog.findViewById(R.id.editSubject);
				editBody = (EditText) dialog.findViewById(R.id.editBody);
				// set the custom dialog components - text, image and button
				TextView toText = (TextView) dialog.findViewById(R.id.TextTo);
				final String emailAddress = (getIntent().getExtras().getString("Email"));
				toText.setText(emailAddress);
	 
				Button dialogButton = (Button) dialog.findViewById(R.id.buttonSendEmail);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						emailSubject = editSubject.getText().toString();
						emailBody = editBody.getText().toString();						
						Intent i = new Intent(Intent.ACTION_SEND);
						i.setType("message/rfc822");
						i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailAddress});
						i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
						i.putExtra(Intent.EXTRA_TEXT   , emailBody);
						try {
						    startActivity(Intent.createChooser(i, "Send mail..."));
						    Toast.makeText(TecherDetails.this, "Email send successfully to "+emailAddress, Toast.LENGTH_SHORT).show();
						} catch (android.content.ActivityNotFoundException ex) {
						    Toast.makeText(TecherDetails.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();
					}
				});
	 
				dialog.show();
				
			}
		});
		
	}
	
	public void ratingMethod()
	{
		buttonRating = (ImageButton) findViewById(R.id.ratingbutton);
		buttonRating.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.rating);
				Drawable d = new ColorDrawable(Color.GRAY);
				d.setAlpha(180);
				dialog.getWindow().setBackgroundDrawable(d);
				
				barPro = (RatingBar) dialog.findViewById(R.id.ratingBarPro);
				barVibility = (RatingBar) dialog.findViewById(R.id.ratingBarAvail);
				barTeach = (RatingBar) dialog.findViewById(R.id.ratingBarTeach);
				barGeneral = (RatingBar) dialog.findViewById(R.id.ratingBarGeneral);
				scorePro = (TextView) dialog.findViewById(R.id.scorePro);
				scoreAvil = (TextView) dialog.findViewById(R.id.scoreAvil);
				scoreTeach = (TextView) dialog.findViewById(R.id.scoreTeach);
				scoreGeneral = (TextView) dialog.findViewById(R.id.scoreGeneral);
				new showRating().execute(new ApiConnector());
				scorePro.setText("("+String.valueOf(df.format(barPro.getRating())+"/5)"));
				scoreAvil.setText("("+String.valueOf(df.format(barVibility.getRating())+"/5)"));
				scoreTeach.setText("("+String.valueOf(df.format(barTeach.getRating())+"/5)"));
				scoreGeneral.setText("("+String.valueOf(df.format((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3)+"/5)"));
				
				barPro.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
					
					@Override
					public void onRatingChanged(RatingBar ratingBar, float rating,
							boolean fromUser) {
						// TODO Auto-generated method stub
						scorePro.setText("("+String.valueOf(df.format(rating))+"/5)");
						barGeneral.setRating((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3);
						scoreGeneral.setText("("+String.valueOf(df.format((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3)+"/5)"));
					}
				});
				
				barVibility.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
					
					@Override
					public void onRatingChanged(RatingBar ratingBar, float rating,
							boolean fromUser) {
						// TODO Auto-generated method stub
						scoreAvil.setText("("+String.valueOf(df.format(rating))+"/5)");
						barGeneral.setRating((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3);
						scoreGeneral.setText("("+String.valueOf(df.format((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3)+"/5)"));
					}
				});
				
				barTeach.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
					
					@Override
					public void onRatingChanged(RatingBar ratingBar, float rating,
							boolean fromUser) {
						// TODO Auto-generated method stub
						scoreTeach.setText("("+String.valueOf(df.format(rating))+"/5)");
						barGeneral.setRating((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3);
						scoreGeneral.setText("("+String.valueOf(df.format((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3)+"/5)"));
					}
				});
				
				
				Button dialogButton = (Button) dialog.findViewById(R.id.buttonRate);
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						if (teacher_rated==false)
							new rate().execute(new ApiConnector());
						else
							new rateUpdate().execute(new ApiConnector());
						dialog.dismiss();
						
					}
				});
				
				dialog.show();
				
			}
			
		});

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
	
	public void callMethod(View v)
	{
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.call_dialog);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);
        Button callYes;
        Button callNo;
        callYes = (Button) dialog.findViewById(R.id.YesCall);
        callNo = (Button) dialog.findViewById(R.id.NoCall);
        dialog.show();
        // If press YES

        //If Press No - close dialog
        callNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        callYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber.replaceAll("[\\s\\-()]", "");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(callIntent);
            }
        });

	}
	
	public void smsMethod(View v)
	{
		phoneNumber.replaceAll("[\\s\\-()]", "");
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                + phoneNumber)));
	}
	
	private class showRating extends AsyncTask<ApiConnector, Long, JSONArray>
	{

		@Override
		protected JSONArray doInBackground(ApiConnector... params) {
			return params[0].showRating();
		}
		
		@Override
		protected void onPostExecute(JSONArray jsonArray)
		{
			if (jsonArray!=null){
				try {
					barPro.setRating(Float.valueOf((String) jsonArray.optJSONObject(0).get("rating_pro")));
					barVibility.setRating(Float.valueOf((String) jsonArray.optJSONObject(0).get("rating_avail")));
					barTeach.setRating(Float.valueOf((String) jsonArray.optJSONObject(0).get("rating_teach")));
					barGeneral.setRating((barPro.getRating() + barVibility.getRating() + barTeach.getRating())/3);
					teacher_rated = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class rate extends AsyncTask<ApiConnector, Long, String>
	{

		@Override
		protected String doInBackground(ApiConnector... params) {
			return params[0].rate();
		}
		
		@Override
		protected void onPostExecute(String msg)
		{
			Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_LONG).show();
		}
		
	}
	
	private class rateUpdate extends AsyncTask<ApiConnector, Long, String>
	{

		@Override
		protected String doInBackground(ApiConnector... params) {
			return params[0].rateUpdate();
		}
		
		@Override
		protected void onPostExecute(String msg)
		{
			Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void showImage(ImageView TeacherImage){
		String urlForImage = baseUrlForImage + (getIntent().getExtras().getString("ImageName"));
		if (!urlForImage.equals(baseUrlForImage+"null") ){
			Picasso.with(context).load(urlForImage).fit().centerCrop().into(TeacherImage);
		}
	}

}
