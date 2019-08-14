package com.atta.currencyrate;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
//todo change sharedpref caps
    private static final String SHARED_PREF = "CurrencyRate";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String IS_LOGIN = "IsLoggedIn";


    private static SessionManager mInstance;
    private Context mContext;

    //alt ins to create constructor for context
    public SessionManager(Context mContext) {

        this.mContext = mContext;
    }

//now we wel create a method that will return the instance of type sharedprefmanager
//which we will pass context in it (as its an instance from sharedprefmanager above)

    public static SessionManager getInstance(Context mContext) {

        if (mInstance == null) {

            mInstance = new SessionManager(mContext);
        }
        return mInstance;
    }

    //now we create method that takes a object from 'User' and create the 'shared preferences'
//'mode private' means that this preferences is only shared in this app, not among all apps
//then we create 'editor' object of 'shared preferences'
//then we put all data from the 'user' object (in this case type & username) inside 'shared preferences'
//then we 'apply'
    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(IS_LOGIN, false);

    }

//this method to return user if logged in
    public User getSavedUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        int id = sharedPreferences.getInt(KEY_ID, -1);
        String username = sharedPreferences.getString(KEY_USERNAME, "gust");
        String password = sharedPreferences.getString(KEY_PASSWORD, null);

        User user = new User(id, username, password);

//TODO set constant keys
        return user;
    }

//this method when user wants to log out
//we will call the shared pref object as above, and also we will call the editor to clear all saved data
//note: editor.clear() is pre defined method, we just created a method with the sama name
    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.putBoolean(IS_LOGIN, false);
        editor.apply();

    }
}
