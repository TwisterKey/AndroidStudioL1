package com.example.logineshopping;

public class Produs {
    private String mName;
    private String mImageUrl;

    public Produs(){

    }

    public Produs(String imageUrl, String name){
        if(name.trim().equals(""))
            name="Fara nume";

        mName=name;
        mImageUrl=imageUrl;
    }

    public String getName(){
        return mName;
    }

    public void setmName(String name){
        mName=name;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
}
