package com.giladsagi.privateteacher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ApiConnector {
	
	String city = DBtest.GetCity();
	String subject = DBtest.GetSubject();
	public static String website = "http://pteacher.hol.es/";


	public String AddTeacher(boolean teacher_created)
	{
		String teacherID = MainActivity.android_id;
		String name = AddTeacher.full;
		String city_i = AddTeacher.s_city;
		ArrayList<teacherSubject> subsArr = AddTeacher.subsArr;
		ArrayList<teacherCity> cityArr = AddTeacher.cityArr;
		String info = AddTeacher.info;
		int age = AddTeacher.s_age; 
		String gender = AddTeacher.s_gender;
		String telephone = AddTeacher.phone1;
		String email = AddTeacher.email;
		Bitmap image = AddTeacher.image;
		String entityResponse = "";
		info = info.replaceAll("[\r\n]+", " ");
		List<NameValuePair> params = AddTeacher.params;
		String selectedImagePath = AddTeacher.selectedImagePath;
		String url = null;
			if (teacher_created==false)
				url = website+"addTeacher.php?teacherID="+teacherID+"&name="+name+"&info="+info+"&age="+age+"&gender="+gender+"&telephone="+telephone+"&email="+email;
			else
				url = website+"updateTeacher.php?teacherID="+teacherID+"&name="+name+"&info="+info+"&age="+age+"&gender="+gender+"&telephone="+telephone+"&email="+email;
				
			
	        url = url.replaceAll(" ", "%20");
	        
			HttpEntity httpEntity = processURL(url);
			
			JSONArray jsonArray = null;
			if (httpEntity != null) {
			try {
            	entityResponse = EntityUtils.toString(httpEntity);
            	Log.e("Entity Response  : ", entityResponse);
            	
				jsonArray = new JSONArray(entityResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
			url = website+"deleteSubject.php?teacherID="+teacherID;

	        url = url.replaceAll(" ", "%20");
	        httpEntity = processURL(url);
			
				for (int i=0; i<subsArr.size(); i++)
				{
					String subject = subsArr.get(i).getSubject();
					int price = subsArr.get(i).getPrice();
	
						url = website+"addSubject.php?teacherID="+teacherID+"&subject="+subject+"&price="+price;

			        url = url.replaceAll(" ", "%20");
			        httpEntity = processURL(url);
				}
				
				url = website+"deleteCity.php?teacherID="+teacherID;

		        url = url.replaceAll(" ", "%20");
		        httpEntity = processURL(url);
				
					for (int i=0; i<cityArr.size(); i++)
					{
						String city = cityArr.get(i).getCity();
		
							url = website+"addCity.php?teacherID="+teacherID+"&city="+city;

				        url = url.replaceAll(" ", "%20");
				        httpEntity = processURL(url);
					}		
			    }
			
			//return entityResponse.substring(0, entityResponse.length()-154);
			return entityResponse;
		}
	
    public Boolean uploadImageToserver(List<NameValuePair> params) {

        // URL for getting all customers
        String url = website+"addImage.php";



        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            String entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response  : ", entityResponse);
            return true;

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Her


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }	
    
    public Boolean deleteImageFromserver(List<NameValuePair> params) {

        String url = website+"deleteImage.php";



        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            String entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response  : ", entityResponse);
            return true;

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Her


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    public void CheckCity()
    {
    	if (city.equals("כל אזור הצפון") || city.equals("צפון"))
    		city = "עכו - נהריה והסביבה','גליל עליון','הכנרת והסביבה','כרמיאל והסביבה','נצרת והסביבה','ראש פינה','רמת הגולן','גליל תחתון','קריית שמונה והסביבה";
    	
    	if (city.equals("כל אזור כרמל ועמקים") || city.equals("אזור כרמל והעמקים"))
    		city = "חיפה והסביבה','קריות והסביבה','זכרון וחוף הרכמל','עפולה והעמקים','עמק בית שאן','רמת מנשה','קיסריה והסביבה','בנימינה','יקנעם והסביבה";
    	
    	if (city.equals("כל אזור השרון והשומרון") || city.equals("אזור השרון והשומרון"))
    		city = "חדרה והסביבה','נתניה והסביבה','יישובי עמק חפר','רמת השרון - הרצליה','רעננה - כפר סבא','הוד השרון והסביבה','אריאל והסביבה','יישובי שומרון";
    	
    	if (city.equals("כל אזור ירושליים ויהודה") || city.equals("אזור ירושליים ויהודה"))
    		city = "ירושליים','אזור לטרון','בית שמש והסביבה','מעלה אדומים והסביבה','מבשרת והסביבה','הרי יהודה והסביבה','גוש עציון','בקעת הירדן','דרום הר חברון";
    	
    	if (city.equals("כל אזור המרכז") || city.equals("מרכז"))
    		city = "תל אביב - יפו','רמת גן - גבעתיים','פתח תקווה והסביבה','חולון - בת ים','ראשון לציון והסביבה','בקעת אונו','ראש העין והסביבה','רמלה - לוד','גבעת שמואל - בני ברק','שוהם והסביבה','מודיעין והסביבה";
    	
    	if (city.equals("כל אזור השפלה ומישור החוף הדרומי") || city.equals("שפלה ומישור החוף הדרומי"))
    		city = "נס ציונה - רחובות','אשדוד והסביבה','אשקלון והסביבה','גדרה - יבנה','קריית גת והסביבה";
    	
    	if (city.equals("כל אזור הדרום") || city.equals("דרום"))
    		city = "באר שבע והסביבה','אילת והערבה','אזור ים המלח - ערד','שדרות - נתיבות','עוטף עזה','ירוחם - דימונה','מצפה ויישובי הנגב";
    }
    
    public void CheckSubject()
    {
    	if (subject.equals("כל ענפי הספורט") || subject.equals("ספורט"))
    		subject = "יוגה','אומנויות לחימה','קונג פו','פילאטיס','רצועות TRX','עיצוב וחיטוב','אימון אישי','שחייה','ספורט אחר";
    	
    	if (subject.equals("כל תחומי המוזיקה") || subject.equals("מוזיקה"))
    		subject = "גיטרה','פסנתר','תופים','כלי נשיפה','כלי קשת','שירה ופיתוח קול','מוזיקה אחר";    
    	
    	if (subject.equals("כל מקצועות בית הספר") || subject.equals("מקצועות בית ספר"))
    		subject = "מתמטיקה','פיסיקה','כימיה','ביולוגיה','היסטוריה','אזרחות','לשון','תנך','ספרות','מחשבים','מקצועות נוספים";    
    	
    	if (subject.equals("כל השפות") || subject.equals("שפות"))
    		subject = "אנגלית','עברית','רוסית','ערבית','ספרדית','צרפתית','שפה אחרת";    
    	
    	if (subject.equals("כל תחומי המחשב") || subject.equals("יישומי המחשב"))
    		subject = "שפות תכנות','בניית אתרים','פיתוח אפליקציות','יישומי Office','עיצוב גרפי','מחשבים אחר";    
    	
    	if (subject.equals("כל מקצועות האקדמיה") || subject.equals("אקדמיה"))
    		subject = "חשבון דיפרנציאלי ואינטגרלי','לינארית','כלכלה','הנדסה כללי','חשבונאות','חשמל','לינארית','הנדסת בניין','הנדסת תעשייה וניהול','הנדסת מערכות מידע','הנדסת חשמל','הנדסת תקשורת','הנדסת חומרים','הנדסת מכונות','הנדסת תוכנה','אקדמיה אחר";

        if (subject.equals("שונות"))
            subject = "תחום אחר','בישול','איפור','משחק','קולנוע','תקשרות','צילום','אומנות";
    }

    public JSONArray GetAllTeachers()
    {
    	CheckCity();
    	CheckSubject();
        String url = website+"getAllTeachers.php?city="+city+"&subject="+subject;;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
    }
    
    public JSONArray GetTeachersByPrice()
    {
    	CheckCity();
    	CheckSubject();    	
        String url = website+"getTeachersByPrice.php?city="+city+"&subject="+subject;;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
    }
    
    public JSONArray GetTeachersByRating()
    {
    	CheckCity();
    	CheckSubject();    	
        String url = website+"getTeachersByRating.php?city="+city+"&subject="+subject;;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
    }
    
    public JSONArray GetTeachersByAge()
    {
    	CheckCity();
    	CheckSubject();    	
        String url = website+"getTeachersByAge.php?city="+city+"&subject="+subject;;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
    }
    
    public JSONArray processJSON(String url){
        

    	HttpEntity httpEntity = processURL(url);
        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }
    
    public HttpEntity processURL(String url)
    {
    	HttpEntity httpEntity = null;
		try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();

        } 
		catch (ClientProtocolException e) 
		{

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here
       	} 
		catch (IOException e) 
        {
	        e.printStackTrace();
        }
		return httpEntity;
    }
    
    public JSONArray showRating()
    {
    	String android_id = MainActivity.android_id;
    	String teacherID = TecherDetails.teacherID;
        String url = website+"showRating.php?android_id="+android_id+"&teacherID="+teacherID;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
    }
    
    public String rate()
    {
    	String android_id = MainActivity.android_id;
    	String teacherID = TecherDetails.teacherID;
    	float ratingPro = TecherDetails.barPro.getRating();
    	float ratingAvil = TecherDetails.barVibility.getRating();
    	float ratingTeach = TecherDetails.barTeach.getRating();
    	float ratingGeneral = TecherDetails.barGeneral.getRating();
    	
		String url = null;
		url = website+"rate.php?android_id="+android_id+"&teacherID="+teacherID+"&ratingPro="+ratingPro+"&ratingAvil="+ratingAvil+"&ratingTeach="+ratingTeach+"&ratingGeneral="+ratingGeneral;
		
        url = url.replaceAll(" ", "%20");
        String entityResponse = "";
		HttpEntity httpEntity = processURL(url);
		
		if (httpEntity != null) {
                try {
					entityResponse = EntityUtils.toString(httpEntity);
					Log.e("Entity Response  : ", entityResponse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } 
		if (entityResponse.equals("1")){
			entityResponse = "הדירוג התקבל, תודה";
		}
		return entityResponse;
    }
    
    public String rateUpdate()
    {
    	String android_id = MainActivity.android_id;
    	String teacherID = TecherDetails.teacherID;
    	float ratingPro = TecherDetails.barPro.getRating();
    	float ratingAvil = TecherDetails.barVibility.getRating();
    	float ratingTeach = TecherDetails.barTeach.getRating();
    	float ratingGeneral = TecherDetails.barGeneral.getRating();
    	
		String url = null;
		url = website+"rateupdate.php?android_id="+android_id+"&teacherID="+teacherID+"&ratingPro="+ratingPro+"&ratingAvil="+ratingAvil+"&ratingTeach="+ratingTeach+"&ratingGeneral="+ratingGeneral;
		
        url = url.replaceAll(" ", "%20");
        String entityResponse = "";
		HttpEntity httpEntity = processURL(url);
		
		if (httpEntity != null) {
                try {
					entityResponse = EntityUtils.toString(httpEntity);
					Log.e("Entity Response  : ", entityResponse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } 
		if (entityResponse.equals("1")){
			entityResponse = "הדירוג התקבל, מעדכן דירוג קודם, תודה";
		}		
		return entityResponse;
    }
    

	public JSONArray showTeacher() {
    	String android_id = MainActivity.android_id;
    	String query = "1";
        String url = website+"showTeacher.php?android_id="+android_id+"&query="+query;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
	}
	
	public JSONArray showTeacher2() {
    	String android_id = MainActivity.android_id;
    	String query = "2";
        String url = website+"showTeacher.php?android_id="+android_id+"&query="+query;
        url = url.replaceAll(" ", "%20");
        return processJSON(url);
	}	

	public String DeleteTeacher() {
		String android_id = MainActivity.android_id;
		String url = null;
		url = website+"deleteTeacher.php?android_id="+android_id;
		
        url = url.replaceAll(" ", "%20");
        String entityResponse = "";
		HttpEntity httpEntity = processURL(url);
		
		if (httpEntity != null) {
                try {
					entityResponse = EntityUtils.toString(httpEntity);
					Log.e("Entity Response  : ", entityResponse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } 
		if (entityResponse.equals("1")){
			entityResponse = "המורה נמחק בהצלחה";
		}
		
		return entityResponse;
	}

}

