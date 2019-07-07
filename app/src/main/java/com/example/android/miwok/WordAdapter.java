package com.example.android.miwok;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ankit Raj on 30-Mar-18.
 */

public class WordAdapter extends ArrayAdapter<Words> {
    private int mColorResourceId;
    public WordAdapter(Activity context, ArrayList<Words> words,int colorId){
            super(context,0,words);
            mColorResourceId=colorId;
    }
    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        Words currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        //ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        //iconView.setImageResource(currentWord.getImageResourceId());

        ImageView imageView= (ImageView) listItemView.findViewById(R.id.img_view);


        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getImage());
            imageView.setVisibility(View.VISIBLE);
        }

        else {
            imageView.setVisibility(View.GONE);
        }



        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);
        //textContainer.setBackgroundResource(Color.parseColor(String.valueOf(mColorResourceId))); ... is also wrong

       // textContainer.setBackgroundResource(color);   ... is wrong

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView




  //      //self modification to handle touch gestures on custom list item


        return listItemView;
    }
    }
