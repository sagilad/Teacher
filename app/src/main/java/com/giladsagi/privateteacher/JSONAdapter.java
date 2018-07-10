package com.giladsagi.privateteacher;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;




public class JSONAdapter extends BaseAdapter implements ListAdapter {
	
	private static final String baseUrlForImage = ApiConnector.website + "images/";
    private final Activity activity;
    private final JSONArray jsonArray;
    public JSONAdapter (Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
    }

	@Override
	public int getCount() {
        if(null==jsonArray) 
            return 0;
           else
           return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {
        if(null==jsonArray) return null;
        else
          return jsonArray.optJSONObject(position);
	}

	@Override
	public long getItemId(int position) {
        JSONObject jsonObject = (JSONObject) getItem(position);
        return jsonObject.optLong("id");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	       if (convertView == null)
	            convertView = activity.getLayoutInflater().inflate(R.layout.result_layout, null);

	        TextView t_name =(TextView)convertView.findViewById(R.id.teacher_name);
	        TextView t_price =(TextView)convertView.findViewById(R.id.teacher_price);
	        TextView t_rating =(TextView)convertView.findViewById(R.id.teacher_raitings);
	        ImageView t_image =(ImageView)convertView.findViewById(R.id.imageViewTeacher);
	        JSONObject json_data = (JSONObject) getItem(position);  
	        if(null!=json_data )
	        {
	        	String S_t_name;
	        	String S_t_age;
	        	String S_t_gender;
	        	String S_t_price;
	        	String S_t_rating;
	        	String urlForImage;
				try {
					S_t_name = json_data.getString("fullname");
					t_name.setText(S_t_name);
					
					S_t_price = json_data.getString("price");
					t_price.setText(S_t_price);
					
					S_t_rating = json_data.getString("rating_avg");
					if (S_t_rating.equals("null"))
						t_rating.setText("לא דורג");
					else
					{
						if (S_t_rating.length()>3)
						{
							t_rating.setText(S_t_rating.substring(0, 3));
						}
						else
						{
							t_rating.setText(S_t_rating);
						}
					}
					
					urlForImage = baseUrlForImage + json_data.getString("ImageName");
					if (!urlForImage.equals(baseUrlForImage+"null") ){
					new DownloadImageTask(t_image).execute(urlForImage);
					}
						
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return convertView;

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
              if (new_height != 0 && new_width !=0){    // fix issue that it is calling it with 0 height and width in additional from some reason ?
	              Bitmap newbitMap = Bitmap.createScaledBitmap(result,new_width,new_height, true);
				  bmImage.setImageBitmap(newbitMap);
              }
		  }
		}	
	
}
