package com.random.chat.mingle1;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesL {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    private static final String PREF_NAME = "com.random.chat.app.j6uybihbuy7788yo9";
    public  SharedPreferencesL(Context context) {
        if (context == null) {
            return;
        }
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private static final String USERNAME = "USERNAME";

    private static final String USERID = "USERID";

    private static final String GENDER = "GENDER";
    private static final String AGE = "AGE";


    //values
    public void setUserName(String UserName) {
        editor.putString(USERNAME, UserName);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USERNAME, "null");
    }


    public String getUserId() {
        return sharedPreferences.getString(USERID, "null");
    }

    public void setUserId(String UserId) {
        editor.putString(USERID, UserId);
        editor.commit();
    }

    public  String getUserGender() {
       return sharedPreferences.getString(GENDER,"null");
    }
    public void setUserGender(String Gender){
        editor.putString(GENDER,Gender);
        editor.commit();
    }
    public  String getUserAge() {
        return sharedPreferences.getString(AGE,"null");
    }
    public void setUserAge(String Age){
        editor.putString(AGE,Age);
        editor.commit();
    }
}
