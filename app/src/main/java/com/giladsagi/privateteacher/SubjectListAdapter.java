package com.giladsagi.privateteacher;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



class SubjectListAdapter extends ArrayAdapter<teacherSubject>{


	public SubjectListAdapter(Context context, ArrayList<teacherSubject> subArr) {
		super(context, R.layout.city_subject_list, subArr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(getContext());
		View customView = inflator.inflate(R.layout.city_subject_list, parent, false);
		
		teacherSubject ts = getItem(position);
		TextView CityText = (TextView)customView.findViewById(R.id.city_subject_str);
		ImageView XImage = (ImageView)customView.findViewById(R.id.XImage);
		
		CityText.setText(ts.toString());
		XImage.setImageResource(R.drawable.redx); 
		return customView;
	}

}

