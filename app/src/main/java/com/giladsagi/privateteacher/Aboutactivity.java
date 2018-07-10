package com.giladsagi.privateteacher;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Aboutactivity extends Activity {
	final Context context = this;
	private ImageButton buttonEmail;
	EditText editSubject, editBody;
	public static String emailSubject, emailBody;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutactivity);
		emailMethod();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aboutactivity, menu);
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
				final String emailAddress = "sagilad@gmail.com";
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
						    Toast.makeText(Aboutactivity.this, "Email send successfully to "+emailAddress, Toast.LENGTH_SHORT).show();
						} catch (android.content.ActivityNotFoundException ex) {
						    Toast.makeText(Aboutactivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();
					}
				});
	 
				dialog.show();
				
			}
		});
		
	}	
}
