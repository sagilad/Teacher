package com.giladsagi.privateteacher;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;

/**
 * Created by GILADSAG on 7/25/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private static final String baseUrlForImage = ApiConnector.website + "images/";
    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        String areas = listItem.getCity();
        String fields = listItem.getSubject();
        String areas2 = areas.replace(", " , "\n");
        String fields2 = fields.replace(", " , "\n");

        holder.t_name.setText(listItem.getName());
        holder.t_price.setText(listItem.getPrice());
        holder.t_areas.setText(areas2);
        holder.t_fields.setText(fields2);
        if (listItem.getRating().equals("null"))
            holder.t_rating.setText("לא דורג");
        else{
            if (listItem.getRating().length() > 3)
                holder.t_rating.setText(listItem.getRating().substring(0, 3));
            else
                holder.t_rating.setText(listItem.getRating());
        }
        //Set Image
        Picasso.with(context).load(baseUrlForImage+listItem.getImage()).fit().centerCrop().into(holder.t_image);

        holder.ItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TecherDetails.class);

                String selectedID;
                String selectedName;
                String selectedCity;
                String selectedSubject;
                String selectedAge;
                String selectedPrice;
                String selectedRating;
                String selectedTelephone;
                String selectedInfo;
                String selectedGender;
                String selectedemail;
                String selectedImage;

                    selectedID = listItem.getItem_id();
                    selectedName = listItem.getName();
                    selectedCity = listItem.getCity();
                    selectedSubject = listItem.getSubject();
                    selectedAge = listItem.getAge();
                    selectedPrice = listItem.getPrice();
                    selectedRating = listItem.getRating();
                    selectedTelephone = listItem.getTelephone();
                    selectedInfo = listItem.getInfo();
                    selectedGender = listItem.getGender();
                    selectedemail = listItem.getEmail();
                    selectedImage = listItem.getImage();

                    i.putExtra("TeacherID", selectedID);
                    i.putExtra("Name", selectedName);
                    i.putExtra("City", selectedCity);
                    i.putExtra("Subject", selectedSubject);
                    i.putExtra("Info", selectedInfo);
                    i.putExtra("Age", selectedAge);
                    i.putExtra("Gender", selectedGender);
                    i.putExtra("Price", selectedPrice);
                    i.putExtra("Rating", selectedRating);
                    i.putExtra("Telephone", selectedTelephone);
                    i.putExtra("Email", selectedemail);
                    i.putExtra("ImageName", selectedImage);

                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView t_name;
        public TextView t_areas;
        public TextView t_fields;
        public TextView t_price;
        public TextView t_rating;
        public ImageView t_image;
        public RelativeLayout ItemLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            t_name =(TextView)itemView.findViewById(R.id.teacher_name);
            t_areas=(TextView)itemView.findViewById(R.id.textAreasList);
            t_fields=(TextView)itemView.findViewById(R.id.textFieldsList);
            t_price =(TextView)itemView.findViewById(R.id.teacher_price);
            t_rating =(TextView)itemView.findViewById(R.id.teacher_raitings);
            t_image =(ImageView)itemView.findViewById(R.id.imageViewTeacher);
            ItemLayout = (RelativeLayout)itemView.findViewById(R.id.item_layout);
        }
    }
}
