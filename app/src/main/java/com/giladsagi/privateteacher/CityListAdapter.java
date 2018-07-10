package com.giladsagi.privateteacher;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



class CityListAdapter extends ArrayAdapter<teacherCity>{

	public CityListAdapter(Context context, ArrayList<teacherCity> cityArr) {
		super(context, R.layout.city_subject_list, cityArr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(getContext());
		View customView = inflator.inflate(R.layout.city_subject_list, parent, false);
		
		teacherCity tc = getItem(position);
		TextView CityText = (TextView)customView.findViewById(R.id.city_subject_str);
		ImageView XImage = (ImageView)customView.findViewById(R.id.XImage);
		
		CityText.setText(tc.toString());
		XImage.setImageResource(R.drawable.redx); 
		return customView;
	}

}

