package com.giladsagi.privateteacher;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.w3c.dom.Text;


public class MainActivity extends Activity {
	
	String selected1;
	String selected2;
	private Button ChooseArea, ChooseField;
    ImageButton buttonSearch;
	public static String android_id;
	final Context context = this;
	String city = "אזור לימוד";
	String subject = "תחום לימוד";
    FrameLayout layout_MainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_MainMenu = (FrameLayout) findViewById( R.id.mainmenu);
        layout_MainMenu.getForeground().setAlpha( 0);
    	android_id = Secure.getString(getContentResolver(),Secure.ANDROID_ID);


    }
    
    @Override
    public void onBackPressed() {
	   Intent intent = new Intent(Intent.ACTION_MAIN);
	   intent.addCategory(Intent.CATEGORY_HOME);
	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(intent);
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

    
    public void ButtonEntranceMethod(View v){
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        v.startAnimation(buttonClick);
        new checkProfile().execute(new ApiConnector());
    }
    
    public void ButtonInfoMethod(View v){
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        v.startAnimation(buttonClick);
    	Intent i = new Intent(MainActivity.this,Aboutactivity.class);
    	startActivity(i);
    }

    public void SearchMethod(View v)
    {
        Intent i = new Intent(MainActivity.this,search.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_up,R.anim.no_change);
        layout_MainMenu.getForeground().setAlpha( 80);

    }

    @Override
    public void onResume() {
        super.onResume();
        layout_MainMenu.getForeground().setAlpha( 0);
    }

    private class checkProfile extends AsyncTask<ApiConnector, Long, JSONArray>
    {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].showTeacher();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray!=null){
                Intent i = new Intent(MainActivity.this,AddTeacher.class);
                startActivity(i);
            }
            else{
                Intent i = new Intent(MainActivity.this,AddTeacher.class);
                startActivity(i);
            }

        }
    }

}
