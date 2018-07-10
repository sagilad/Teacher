package com.giladsagi.privateteacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by giladsag on 8/7/2017.
 */

public class search extends Activity {

    String city = "אזור לימוד";
    String subject = "תחום לימוד";
    Button ChooseArea, ChooseField;
    ImageButton buttonSearch, exit;
    String selected1;
    String selected2;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((width),(int)(height*0.3));
        getActionBar().hide();

        getWindow().setGravity(Gravity.BOTTOM);
        exit = (ImageButton) findViewById(R.id.exit);
        ChooseArea = (Button) findViewById(R.id.ChooseArea);
        ChooseField = (Button) findViewById(R.id.ChooseField);
        buttonSearch = (ImageButton) findViewById(R.id.imAddSubject);
        ChooseArea.setText(city);
        ChooseField.setText(subject);

        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ChooseArea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                v.startAnimation(buttonClick);
                Intent i = new Intent(search.this,CityChoose.class);
                i.putExtra("Activity","City");
                startActivityForResult(i, 1);
            }
        });

        ChooseField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                v.startAnimation(buttonClick);
                Intent i = new Intent(search.this,CityChoose.class);
                i.putExtra("Activity","Subject");
                startActivityForResult(i, 2);
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                v.startAnimation(buttonClick);
                if (ChooseArea.getText().toString().equals("אזור לימוד"))
                {
                    valid = false;
                    Toast.makeText(getApplicationContext(), "אנא בחר אזור לחיפוש", Toast.LENGTH_LONG).show();
                }
                if (ChooseField.getText().toString().equals("תחום לימוד"))
                {
                    valid = false;
                    Toast.makeText(getApplicationContext(), "אנא בחר תחום לימוד לחיפוש", Toast.LENGTH_LONG).show();
                }
                if (valid == true)
                {
                    selected1 = city;
                    selected2 = subject;
                    Intent i = new Intent(search.this,DBtest.class);
                    i.putExtra("City", selected1 );
                    i.putExtra("subject", selected2 );
                    startActivity(i);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                city = data.getStringExtra("result");
                ChooseArea.setText(city);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                subject = data.getStringExtra("result");
                ChooseField.setText(subject);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_bottom,R.anim.slide_bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        // Touch events inside are fine.

        super.onTouchEvent(ev);
        overridePendingTransition(R.anim.slide_bottom,R.anim.slide_bottom);
        return false;

    }

}
