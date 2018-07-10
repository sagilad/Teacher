package com.giladsagi.privateteacher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.CalendarContract.Colors;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddTeacher extends ActionBarActivity  {
	
	private ImageButton buttonDelete;
	DBAdapter MyDB;
	EditText fullName, generalInfo, t_num1, t_num2, t_email, editAge;
	RadioButton male, female;
	String city = "הוסף יישוב";
	Spinner age;
	Spinner price;
	RadioGroup gender;
	String selected1;
	String selected2;
	int selected4;
	String rad;
	boolean teacher_created = false;
	String subject = "הוסף תחומי לימוד";
	boolean image_switch;
	
	private static final String baseUrlForImage = ApiConnector.website + "images/";
	public static String teacherID = MainActivity.android_id;
	public static String user;
	public static String pass;
	public static String full;
	public static String phone1;
	public static String s_city;
	public static String sub;
	public static String info;
	public static int s_age=0;
	public static int s_price;
	public static String s_gender;
	public static float rating;
	public static String email;
	public static List<NameValuePair> params;
	public static Bitmap image;
	public static String selectedImagePath;
	public static Uri selectedImage;
	
	public static String idOfTeacher;
	private ImageButton addTeacherButton;
	private ListView lvSubs, lvCity;
	public static ArrayList<teacherSubject> subsArr;
	private ArrayAdapter<teacherSubject> adapterSubject;
	ArrayList<teacherSubject> subsArrTemp;
	
	public static ArrayList<teacherCity> cityArr;
	private ArrayAdapter<teacherCity> adapterCity;
	ArrayList<teacherCity> cityArrTemp;
	
	final Context context = this;
	private EditText buttonCity, buttonSubject;
	private Button buttonSubjectDialog;
	private Button buttonCityDialog;
	ImageView teacherImage;
	int rotateImage;
	
	MyProgressDialog pd;
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_teacher);
		
		View view = this.getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		pd = new MyProgressDialog(AddTeacher.this);
		pd.setMessage("טוען...");
		pd.show();
		
//		openDB();
		subsArr = new ArrayList<teacherSubject>();
		subsArrTemp = new ArrayList<teacherSubject>();
		cityArr = new ArrayList<teacherCity>();
		cityArrTemp = new ArrayList<teacherCity>();
		teacherImage = (ImageView) findViewById(R.id.imageViewTeacher);
		params = new ArrayList<NameValuePair>();
		
		image_switch = false;
		
		fullName=(EditText) findViewById(R.id.fullNameValue);
		fullName.setSelected(false);
		buttonCity = (EditText) findViewById(R.id.cityButton);
		buttonCity.setText(city);
		buttonCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addCityMethod();
			}
		});
		

		buttonSubject = (EditText) findViewById(R.id.imAddSubject);
		buttonSubject.setText(subject);
		buttonSubject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addSubjectMethod();
				
			}
		});
		
		addTeacherButton = (ImageButton) findViewById(R.id.addTeacherButton);
		addTeacherButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTeacherMethod();
			}
		});
				
		
		editAge=(EditText) findViewById(R.id.editAge);
		t_num1=(EditText) findViewById(R.id.t_num1);
		t_email=(EditText) findViewById(R.id.editEmail);
		gender=(RadioGroup) findViewById(R.id.rgroup);
		male = (RadioButton) findViewById(R.id.radioButton1);
		female = (RadioButton) findViewById(R.id.radioButton2);
		new showTeacher().execute(new ApiConnector());
		new showTeacher2().execute(new ApiConnector());
				gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				if (male.isChecked())
				{
					rad = "זכר";
				}
				else if (female.isChecked())
				{
					rad = "נקבה";
				}
				else
				{
					rad = "Not Specified";
				}
				
			}
		});		
		generalInfo=(EditText) findViewById(R.id.generalInfoValue);
		
		teacherImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Teacher Image"), 3);
				
			}
		});
	}
	
    @SuppressLint("NewApi") protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
            	boolean valid = true;
            	city=data.getStringExtra("result");
            	for (int i=0; i<cityArr.size(); i++)
            	{
            		if (cityArr.get(i).getCity().equals(city))
            		{
            			Toast.makeText(getApplicationContext(), "כבר בחרת ביישוב זה", Toast.LENGTH_LONG).show();
            			valid = false;
            		}
            	}
				if (cityArr.size()<4 && valid == true)
				{
					selected1 = city;
					teacherCity tc = new teacherCity();
					tc.setSize(cityArr.size()+1);
					tc.setCity(selected1);
					cityArr.add(tc);
					adapterCity.notifyDataSetChanged();
					buttonCityDialog.setText("הוסף יישוב");
				}
				else if (cityArr.size()>=4)
					Toast.makeText(getApplicationContext(), "הגעת למקסימום 4 יישובים", Toast.LENGTH_LONG).show();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
            	subject=data.getStringExtra("result");
            	boolean valid = true;
            	for (int i=0; i<subsArr.size(); i++)
            	{
            		if (subsArr.get(i).getSubject().equals(subject))
            		{
            			Toast.makeText(getApplicationContext(), "כבר בחרת בתחום לימוד זה", Toast.LENGTH_LONG).show();
            			valid = false;
            		}
            	}
				if (subsArr.size()<4 && valid == true)
				{
					selected2 = subject;
					// custom dialog
					final Dialog dialog = new Dialog(context);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.price);
					Drawable d = new ColorDrawable(Color.BLACK);
					d.setAlpha(130);
					dialog.getWindow().setBackgroundDrawable(d);
					dialog.show();
					final EditText spPrice = (EditText) dialog.findViewById(R.id.spPrice);
					spPrice.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
					Button OkPrice;
					OkPrice = (Button) dialog.findViewById(R.id.OkPrice);
					OkPrice.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
						if (spPrice.getText().toString().trim().length() > 0)
						{
							selected4 = Integer.valueOf(spPrice.getText().toString());
							teacherSubject ts = new teacherSubject();
							ts.setSize(subsArr.size()+1);
							ts.setSubject(selected2);
							ts.setPrice(selected4);
							subsArr.add(ts);
							adapterSubject.notifyDataSetChanged();
							spPrice.getText().clear();
							spPrice.setSelected(false);
						    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
							dialog.dismiss();
						}
						else
							Toast.makeText(getApplicationContext(), "אנא ציין מחיר לשעה", Toast.LENGTH_LONG).show();
						}
					});
				}
				else if (subsArr.size()>=4)
					Toast.makeText(getApplicationContext(), "הגעת למקסימום 4 מקצועות", Toast.LENGTH_LONG).show();            	
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }  
        
        if (requestCode == 3){
        	if(resultCode == RESULT_OK){
        		selectedImage = data.getData();
        		
        		selectedImagePath = getPath(context, selectedImage);
 //       		Toast.makeText(getApplicationContext(), "selectedImagePath "+selectedImagePath, Toast.LENGTH_LONG).show();


        		
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Toast.makeText(getApplicationContext(), "selectedImage "+selectedImage, Toast.LENGTH_LONG).show();
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
                rotateImage = getCameraPhotoOrientation(AddTeacher.this, selectedImage, selectedImagePath);
                teacherImage.setRotation(rotateImage);
                Bitmap bitMap = BitmapFactory.decodeFile(selectedImagePath);
                int bmWidth=bitMap.getWidth();
                int bmHeight=bitMap.getHeight();
                int ivWidth=teacherImage.getWidth();
                int ivHeight=teacherImage.getHeight();
                int new_height;
                int new_width;
                if (bmWidth>bmHeight){
                	new_height=ivHeight;
                	new_width = (int) Math.floor((double) bmWidth *( (double) new_height / (double) bmHeight));
                }
                else {
                	new_width=ivWidth;
                	new_height = (int) Math.floor((double) bmHeight *( (double) new_width / (double) bmWidth));                	
                }
                Bitmap newbitMap = Bitmap.createScaledBitmap(bitMap,new_width,new_height, true);
                teacherImage.setImageBitmap(newbitMap);
//                teacherImage.setImageURI(selectedImage);
//                Toast.makeText(getApplicationContext(), "selectedImagePath "+selectedImagePath, Toast.LENGTH_LONG).show();
//        		
        		
        	}
        	
        }
        
    }//onActivityResult
	
//	private void openDB() {
//		MyDB = new DBAdapter(this);
//		MyDB.open();
//
//	}
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//
//		closeDB();
//	}
//
//
//	private void closeDB() {
//		MyDB.close();
//
//	}
	
    private void SetImage(Bitmap image) {
    	Matrix matrix = new Matrix();
    	matrix.postRotate(rotateImage);
    	
    	image = BitmapFactory.decodeFile(selectedImagePath);
    	image = resizeBitMapImage1(selectedImagePath, 400, 400);
    	Bitmap rotatedBitmap = Bitmap.createBitmap(image , 0, 0, image .getWidth(), image .getHeight(), matrix, true);
    	this.teacherImage.setImageBitmap(image);
        // upload
    	
        String imageData = encodeTobase64(rotatedBitmap);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (idOfTeacher == null){
        	idOfTeacher=teacherID;
        }
        params.add(new BasicNameValuePair("image", imageData));
        params.add(new BasicNameValuePair("teacherID", idOfTeacher));
        
        new AsyncTask<ApiConnector,Long, Boolean >() {
            @Override
            protected Boolean doInBackground(ApiConnector... apiConnectors) {
                return apiConnectors[0].uploadImageToserver(params);
            }
        }.execute(new ApiConnector());        
    }
    
    public static String encodeTobase64(Bitmap image) {
        System.gc();

        if (image == null)return null;

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();

        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT); // min minSdkVersion 8

        return imageEncoded;
    }    
    
    public String getPath2(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }    
	
	@SuppressLint("NewApi") public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}
	
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int column_index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(column_index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());	
	}
   
	
	public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
	    int rotate = 0;
	    try {
	        context.getContentResolver().notifyChange(imageUri, null);
	        File imageFile = new File(imagePath);

	        ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
	        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
	        image_switch = true;

	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            rotate = 270;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            rotate = 180;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            rotate = 90;
	            break;
	        }

	        Log.i("RotateImage", "Exif orientation: " + orientation);
	        Log.i("RotateImage", "Rotate value: " + rotate);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return rotate;
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
	
	public void addTeacherMethod()
	{
		boolean valid =true;
		
		if (fullName.getText().toString().trim().length() <= 0)
		{
			Toast.makeText(getApplicationContext(), "שם המורה חייב להכיל יותר מתו אחד", Toast.LENGTH_LONG).show();
			fullName.setHintTextColor(Color.RED);
			fullName.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}
		if (editAge.getText().toString().length() > 0)
		{
			s_age = Integer.valueOf(editAge.getText().toString());
			if (s_age<7 || s_age>120)
			{
				Toast.makeText(getApplicationContext(), "גיל המורה חייב להיות בין 7 ל 120", Toast.LENGTH_LONG).show();
				editAge.setTextColor(Color.RED);
				editAge.setHintTextColor(Color.RED);
				valid = false;
				if (pd.isShowing())
					pd.dismiss();				
			}		
		}
		else
		{
			Toast.makeText(getApplicationContext(), "גיל המורה חייב להיות בין 7 ל 120", Toast.LENGTH_LONG).show();
			editAge.setHintTextColor(Color.RED);
			editAge.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}
		if (t_num1.getText().toString().trim().length() <= 0)
		{
			Toast.makeText(getApplicationContext(), "יש להכניס מספר טלפון", Toast.LENGTH_LONG).show();
			t_num1.setHintTextColor(Color.RED);
			t_num1.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();	
		}
		else
		{
			phone1=t_num1.getText().toString();
			phone1 = phone1.replaceAll("[\\s\\-()]", "");
			if (!isInteger(phone1))
			{
				Toast.makeText(getApplicationContext(), "מספר טלפון אינו חוקי", Toast.LENGTH_LONG).show();
				t_num1.setTextColor(Color.RED);
				t_num1.setHintTextColor(Color.RED);
				valid = false;
				if (pd.isShowing())
					pd.dismiss();			
			}		
		}
		if (t_email.getText().toString().trim().length() <= 4)
		{
			Toast.makeText(getApplicationContext(), "יש להכניס כתובת אימייל", Toast.LENGTH_LONG).show();
			t_email.setHintTextColor(Color.RED);
			t_email.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}
		if (gender.getCheckedRadioButtonId() == -1)
		{
			Toast.makeText(getApplicationContext(), "יש לבחור מגדר", Toast.LENGTH_LONG).show();
			male.setTextColor(Color.RED);
			female.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}		
		
		if (buttonCity.getText().toString().equals("הוסף יישוב"))
		{
			Toast.makeText(getApplicationContext(), "חובה לבחור יישוב אחד לפחות", Toast.LENGTH_LONG).show();
			buttonCity.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}
		if (buttonSubject.getText().toString().equals("הוסף תחומי לימוד"))
		{
			Toast.makeText(getApplicationContext(), "חובה לבחור תחום לימוד אחד לפחות", Toast.LENGTH_LONG).show();
			buttonSubject.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}
		if (generalInfo.getText().toString().trim().length() <= 0)
		{
			Toast.makeText(getApplicationContext(), "אנא ספר קצת על עצמך", Toast.LENGTH_LONG).show();
			generalInfo.setHintTextColor(Color.RED);
			generalInfo.setTextColor(Color.RED);
			valid = false;
			if (pd.isShowing())
				pd.dismiss();			
		}		
		if (valid == true)
		{
			full=fullName.getText().toString();		
			email=t_email.getText().toString();
			s_city=buttonCity.getText().toString();
			info=generalInfo.getText().toString();
			s_gender = rad;
			rating = 0;
			
			final Dialog dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.contract);
			Drawable d = new ColorDrawable(Color.BLACK);
			d.setAlpha(130);
			dialog.getWindow().setBackgroundDrawable(d);
			Button buttonYes1;
			Button buttonNo1;
			buttonNo1 = (Button) dialog.findViewById(R.id.buttonNo2);
			buttonYes1 = (Button) dialog.findViewById(R.id.buttonYes1);
			final CheckBox checkcontract;
			checkcontract = (CheckBox) dialog.findViewById(R.id.checkBox1);
			//checkcontract.setTextColor(Color.RED);
			//checkcontract.setBackgroundColor(Color.BLUE);
			dialog.show();

			
			//If Press No - close dialog
			buttonNo1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (pd.isShowing())
						pd.dismiss();
					dialog.dismiss();
				}
			});
			// If press YES			
			buttonYes1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (checkcontract.isChecked())
					{
						pd = new MyProgressDialog(AddTeacher.this);
						pd.setMessage("טוען...");
						pd.show();
						
						
					
						
						new addTeacher().execute(new ApiConnector());
						dialog.dismiss();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "יש לאשר שקראת את התקנון והסכמת לכל פרטיו", Toast.LENGTH_LONG).show();
					}
			
				}
			});
			
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}	
	
//	@Override
//	public void onItemSelected(AdapterView<?> parent, View view, int position,
//			long id) {
//		Spinner spinner =  (Spinner) parent;
//		if (spinner.getId()==R.id.priceSpinnerValue) {
//			selected4 = Integer.valueOf(parent.getItemAtPosition(position).toString());
//		}
//		
//	}
//	@Override
//	public void onNothingSelected(AdapterView<?> parent) {
//		// TODO Auto-generated method stub	
//	}
	
	private class addTeacher extends AsyncTask<ApiConnector, Long, String>
	{

		@Override
		protected String doInBackground(ApiConnector... params) {
			return params[0].AddTeacher(teacher_created);
		}
		
		@Override
		protected void onPostExecute(String msg)
		{
//			JSONObject teacher;
//			try {
//				teacher = jsonArray.getJSONObject(0);
//				String urlForImage = baseUrlForImage + teacher.getString("ImageName");
//				new DownloadImageTask(teacherImage).execute(urlForImage);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			if (pd.isShowing())
				pd.dismiss();
//			String msg ="";
//			if (jsonArray!=null)
//			{
//				msg  = "Your data has been saved";
//			}
			if (msg.equals("Your data has been saved"))
			{
				Toast.makeText(getApplicationContext(), "פרטי המורה נשמרו בהצלחה", Toast.LENGTH_LONG).show();
				if (image_switch == true){
	    		if (Build.VERSION.SDK_INT < 19) {
	        		selectedImagePath = getPath2(selectedImage);
	        		Bitmap bitmap=null;

	        		SetImage(bitmap);
	        		image = bitmap;
	        		} 
	        		else {
	                    ParcelFileDescriptor parcelFileDescriptor;
	                    try {
	                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImage, "r");
	                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
	                        image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
	                        parcelFileDescriptor.close();
	                        SetImage(image);
	                    } catch (FileNotFoundException e) {
	                        e.printStackTrace();
	                    } catch (IOException e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }	
				}
				Intent i = new Intent(AddTeacher.this,MainActivity.class);
				startActivity(i);
			}
			else if (msg.equals("No Image Selected"))
			{
				Toast.makeText(getApplicationContext(), "פרטי המורה נשמרו בהצלחה ללא תמונה", Toast.LENGTH_LONG).show();
				Intent i = new Intent(AddTeacher.this,MainActivity.class);
				startActivity(i);
			}			
			else
			{
				Toast.makeText(getApplicationContext(),"שגיאה - פרטי המורה לא נשמרו" , Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void deleteTeacherMethod(View view)
	{
		Toast.makeText(getApplicationContext(), "לחצ/י עוד פעם על מנת להימחק ממאגר המורים", Toast.LENGTH_LONG).show();
		buttonDelete = (ImageButton) findViewById(R.id.deleteImage);
		buttonDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.delete);
				Drawable d = new ColorDrawable(Color.BLACK);
				d.setAlpha(130);
				dialog.getWindow().setBackgroundDrawable(d);
				Button deleteYes;
				Button deleteNo;
				deleteYes = (Button) dialog.findViewById(R.id.YesDelete);
				deleteNo = (Button) dialog.findViewById(R.id.NoDelete);
				dialog.show();
				// If press YES
				
				//If Press No - close dialog
				deleteNo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}
				});
				
				deleteYes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new deleteTeacher().execute(new ApiConnector());
				        if (idOfTeacher == null){
				        	idOfTeacher=teacherID;
				        }						
				        params.add(new BasicNameValuePair("teacherID", idOfTeacher));
				        new AsyncTask<ApiConnector,Long, Boolean >() {
				            @Override
				            protected Boolean doInBackground(ApiConnector... apiConnectors) {
				                return apiConnectors[0].deleteImageFromserver(params);
				            }
				        }.execute(new ApiConnector());        
				        
						dialog.dismiss();
						Intent i = new Intent(AddTeacher.this,MainActivity.class);
						startActivity(i);
					}
				});
				

			}
		});

	}
	
	private class deleteTeacher extends AsyncTask<ApiConnector, Long, String>
	{

		@Override
		protected String doInBackground(ApiConnector... params) {
			return params[0].DeleteTeacher();
		}
		
		@Override
		protected void onPostExecute(String msg)
		{
			Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_LONG).show();
		}
	}	
	
	@SuppressWarnings("unchecked")
	void addSubjectMethod(){
		subsArr = (ArrayList<teacherSubject>) subsArrTemp.clone();
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.addsubject);
		Drawable d = new ColorDrawable(Color.BLACK);
		d.setAlpha(130);
		dialog.getWindow().setBackgroundDrawable(d);
		Button buttonOk;
		buttonOk = (Button) dialog.findViewById(R.id.buttonSubmit1);
		buttonSubjectDialog = (Button) dialog.findViewById(R.id.ButtonSubjectDialog);
		buttonSubjectDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddTeacher.this,CityChoose.class);
				i.putExtra("Activity","Subject1");
		    	startActivityForResult(i, 2);
			}
		});
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.stra_subject,R.layout.spinner_item);
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.stra_price,R.layout.spinner_item);
		
		lvSubs = (ListView) dialog.findViewById(R.id.listViewSujects);
//		adapter = new ArrayAdapter<teacherSubject>(getApplicationContext(), android.R.layout.simple_list_item_1, subsArr);
		adapterSubject = new SubjectListAdapter(this, subsArr);
		lvSubs.setAdapter(adapterSubject);
		for (int i=0; i < subsArr.size(); i++)
		{
			subsArr.get(i).setSize(i+1);
		}
		adapterSubject.notifyDataSetChanged();

		buttonOk.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (subsArr.size()==0)
				{
					Toast.makeText(getApplicationContext(), "חובה לבחור לפחות מקצוע אחד", Toast.LENGTH_LONG).show();
				}
				else
				{
					subsArrTemp = (ArrayList<teacherSubject>) subsArr.clone();
					StringBuilder sublist = new StringBuilder();
					for (int i=0; i<subsArr.size()-1; i++)
					{
						sublist.append(subsArr.get(i).getSubject()+"\n");
					}
					sublist.append(subsArr.get(subsArr.size()-1).getSubject());
					buttonSubject.setText(sublist.toString());
					dialog.dismiss();
				}
			}
		});
		dialog.show();
		registerListClockCallback(dialog);
	}
	private void registerListClockCallback(Dialog dialog) {
		ListView lv = (ListView) dialog.findViewById(R.id.listViewSujects);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				subsArr.remove(position);
				for (int i=position; i < subsArr.size(); i++)
				{
					subsArr.get(i).setSize(i+1);
				}
				adapterSubject.notifyDataSetChanged();
			}
			
		});
	}
	
	
	@SuppressWarnings("unchecked")
	void addCityMethod(){
		cityArr = (ArrayList<teacherCity>) cityArrTemp.clone();
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.addcity);
		Drawable d = new ColorDrawable(Color.BLACK);
		d.setAlpha(130);
		dialog.getWindow().setBackgroundDrawable(d);
		Button buttonOk;
		buttonOk = (Button) dialog.findViewById(R.id.buttonSubmitCity);
		buttonCityDialog = (Button) dialog.findViewById(R.id.ButtonCityDialog);
		buttonCityDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddTeacher.this,CityChoose.class);
				i.putExtra("Activity","City1");
		    	startActivityForResult(i, 1);
			}
		});
		lvCity = (ListView) dialog.findViewById(R.id.listViewCity);
		adapterCity = new CityListAdapter(this, cityArr);
	//	adapterCity = new ArrayAdapter<teacherCity>(getApplicationContext(), android.R.layout.simple_list_item_1, cityArr);
		lvCity.setAdapter(adapterCity);
		//Need to set numbers again.
		for (int i=0; i < cityArr.size(); i++)
		{
			cityArr.get(i).setSize(i+1);
		}
		adapterCity.notifyDataSetChanged();

		buttonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (cityArr.size()==0)
				{
					Toast.makeText(getApplicationContext(), "חובה לבחור לפחות יישוב אחד", Toast.LENGTH_LONG).show();
				}
				else
				{
					cityArrTemp = (ArrayList<teacherCity>) cityArr.clone();
					StringBuilder citylist = new StringBuilder();
					for (int i=0; i<cityArr.size()-1; i++)
					{
						citylist.append(cityArr.get(i).getCity()+"\n");
					}
					citylist.append(cityArr.get(cityArr.size()-1).getCity());
					buttonCity.setText(citylist.toString());
					dialog.dismiss();
				}
			}
		});
		dialog.show();
		registerListClockCallbackCity(dialog);
	}	
	private void registerListClockCallbackCity(Dialog dialog) {
		ListView lv = (ListView) dialog.findViewById(R.id.listViewCity);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cityArr.remove(position);
				//Need to set numbers again.
				for (int i=position; i < cityArr.size(); i++)
				{
					cityArr.get(i).setSize(i+1);
				}
				adapterCity.notifyDataSetChanged();
			}
			
		});
	}	
	
	private class showTeacher extends AsyncTask<ApiConnector, Long, JSONArray>
	{

		@Override
		protected JSONArray doInBackground(ApiConnector... params) {
			// TODO Auto-generated method stub
			return params[0].showTeacher();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(JSONArray jsonArray)
		{
			if (pd.isShowing())
				pd.dismiss();

			
			if (jsonArray!=null){
				JSONObject teacher;
				
				try {
					for (int i=0; i < 2; i++)
					{
					Toast.makeText(getApplicationContext(), "שלום " + jsonArray.optJSONObject(0).get("fullname") + " " + "הינך רשום כמורה, באפשרותך לעדכן את הפרופיל", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					teacher = jsonArray.getJSONObject(0);
					// need to copy into addTeacher method!
					idOfTeacher = teacher.getString("teacherID");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					teacher_created = true;
					
					try {
						fullName.setText((String) jsonArray.optJSONObject(0).get("fullname"));
						editAge.setText((String) jsonArray.optJSONObject(0).get("age"));
						if (jsonArray.optJSONObject(0).get("gender").equals("זכר"))
							male.setChecked(true);
						else
							female.setChecked(true);
						t_num1.setText((String) jsonArray.optJSONObject(0).get("telephone"));
						t_email.setText((String) jsonArray.optJSONObject(0).get("email"));
						generalInfo.setText((String) jsonArray.optJSONObject(0).get("info"));
						
						StringBuilder sublist = new StringBuilder();
						for (int i=0; i<jsonArray.length()-1; i++)
						{
							sublist.append((String) jsonArray.optJSONObject(i).get("subject")+"\n");
							teacherSubject ts = new teacherSubject();
							ts.setSubject((String) jsonArray.optJSONObject(i).get("subject"));
							ts.setPrice(Integer.valueOf((String) jsonArray.optJSONObject(i).get("price")));
							subsArr.add(ts);
						}
						
						teacherSubject ts = new teacherSubject();
						ts.setSubject((String) jsonArray.optJSONObject(jsonArray.length()-1).get("subject"));
						ts.setPrice(Integer.valueOf((String) jsonArray.optJSONObject(jsonArray.length()-1).get("price")));
						subsArr.add(ts);						
						sublist.append((String) jsonArray.optJSONObject(jsonArray.length()-1).get("subject"));
						buttonSubject.setText(sublist.toString());
						subsArrTemp = (ArrayList<teacherSubject>) subsArr.clone();  // save the subject array adapter in a temp var for using it if the user is'nt save the data.
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//setting all edit text with teacher parms!
					
//                  showing teacher image					
					
					try {
						teacher = jsonArray.getJSONObject(0);
						String str = teacher.getString("ImageName");
						if (!str.equals("null")){
						String urlForImage = baseUrlForImage + teacher.getString("ImageName");
						new DownloadImageTask(teacherImage).execute(urlForImage);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();		
					}
					
			}
			else
			{
				if (teacher_created == false)
				{
					final Dialog dialog = new Dialog(context);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.register);
					Drawable d = new ColorDrawable(Color.BLACK);
					d.setAlpha(130);
					dialog.getWindow().setBackgroundDrawable(d);
					Button buttonNo = (Button) dialog.findViewById(R.id.buttonNo);
					Button buttonYes = (Button) dialog.findViewById(R.id.buttonYes);
					
					buttonYes.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
						
					});
					dialog.show();
					
					buttonNo.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
					    	Intent i = new Intent(AddTeacher.this,MainActivity.class);
					    	startActivity(i);
						}
						
					});
					
				}

			}
		}
	}
	
	private class showTeacher2 extends AsyncTask<ApiConnector, Long, JSONArray>
	{
		@Override
		protected JSONArray doInBackground(ApiConnector... params) {
			// TODO Auto-generated method stub
			return params[0].showTeacher2();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(JSONArray jsonArray)
		{
			if (jsonArray!=null){
				try{
					StringBuilder citylist = new StringBuilder();
					for (int i=0; i<jsonArray.length()-1; i++)
					{
						citylist.append((String) jsonArray.optJSONObject(i).get("city")+"\n");
						teacherCity tc = new teacherCity();
						tc.setCity((String) jsonArray.optJSONObject(i).get("city"));
						cityArr.add(tc);
					}
					
					teacherCity tc = new teacherCity();
					tc.setCity((String) jsonArray.optJSONObject(jsonArray.length()-1).get("city"));
					cityArr.add(tc);						
					citylist.append((String) jsonArray.optJSONObject(jsonArray.length()-1).get("city"));
					buttonCity.setText(citylist.toString());
					cityArrTemp = (ArrayList<teacherCity>) cityArr.clone();  // save the subject array adapter in a temp var for using it if the user is'nt save the data.
					}
				catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		  private ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			  this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }

		  @SuppressWarnings("static-access")
		protected void onPostExecute(Bitmap result) {
              int bmWidth=result.getWidth();
              int bmHeight=result.getHeight();
              int ivWidth=bmImage.getWidth();
              int ivHeight=bmImage.getHeight();
              int new_height;
              int new_width;
              if (bmWidth>bmHeight){
              	new_height=ivHeight;
              	new_width = (int) Math.floor((double) bmWidth *( (double) new_height / (double) bmHeight));
              }
              else {
              	new_width=ivWidth;
              	new_height = (int) Math.floor((double) bmHeight *( (double) new_width / (double) bmWidth));                	
              }
              Bitmap newbitMap = Bitmap.createScaledBitmap(result,new_width,new_height, true);			  
			  bmImage.setImageBitmap(newbitMap);
		  }
		}	
	
	public Bitmap resizeBitMapImage1(String filePath, int targetWidth, int targetHeight) {
	    Bitmap bitMapImage = null;
	    try {
	        Options options = new Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);
	        double sampleSize = 0;
	        Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth
	                - targetWidth);
	        if (options.outHeight * options.outWidth * 2 >= 1638) {
	            sampleSize = scaleByHeight ? options.outHeight / targetHeight : options.outWidth / targetWidth;
	            sampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize) / Math.log(2d)));
	        }
	        options.inJustDecodeBounds = false;
	        options.inTempStorage = new byte[128];
	        while (true) {
	            try {
	                options.inSampleSize = (int) sampleSize;
	                bitMapImage = BitmapFactory.decodeFile(filePath, options);
	                break;
	            } catch (Exception ex) {
	                try {
	                    sampleSize = sampleSize * 2;
	                } catch (Exception ex1) {

	                }
	            }
	        }
	    } catch (Exception ex) {

	    }
	    return bitMapImage;
	}	
	
}
