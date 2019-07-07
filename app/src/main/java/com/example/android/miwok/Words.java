package com.example.android.miwok;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Ankit Raj on 30-Mar-18.
 */

public class Words {
    private String mMiwokTranslation,mDefaultTranslation;
    private int mImage=HAS_NO_IMAGE,mMediaId;
    private static  final int HAS_NO_IMAGE=-1;
//    public Words(String defaultTranslation,String miwokTranslation,int image){
//        mMiwokTranslation=miwokTranslation;
//        mDefaultTranslation=defaultTranslation;
//        mImage=image;
//    }

    public Words(String defaultTranslation,String miwokTranslation,int mMediaId){
        mMiwokTranslation=miwokTranslation;
        mDefaultTranslation=defaultTranslation;
        this.mMediaId=mMediaId;
    }

    public Words(String defaultTranslation,String miwokTranslation,int image,int mediaId){
        mMiwokTranslation=miwokTranslation;
        mDefaultTranslation=defaultTranslation;
        mImage=image;
        mMediaId=mediaId;

    }
    public Words(String defaultTranslation,String miwokTranslation){
        mMiwokTranslation=miwokTranslation;
        mDefaultTranslation=defaultTranslation;
    }
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public  String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getImage(){
        return mImage;
    }
    public boolean hasImage(){
        return mImage!=HAS_NO_IMAGE;
    }
    public int getMediaId(){
        return mMediaId;
    }
}
