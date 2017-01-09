package com.example.user.tasklist.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UsersManager {
    private SharedPreferences prefs;
    private final String USER_LOGIN = "UserLogin";

    public UsersManager(Context context)
    {
        this.prefs = context.getSharedPreferences("users",context.MODE_PRIVATE);
    }

    public String whichUserLogin()
    {
        return prefs.getString(USER_LOGIN,null);
    }

    public void login(String username)
    {
        prefs.edit().putString(USER_LOGIN,username).commit();
    }

    public void logout()
    {
        prefs.edit().remove(USER_LOGIN).commit();
    }

    public void addUser(String username, String password)
    {
        //add user to users file - format: <String name=username>password</String>
        prefs.edit().putString(username,password).commit();
    }

    public String getPassword(String user)
    {
        //if user exist - return user password, if user don't exist - return null
        return (prefs.getString(user,null));
    }
}
