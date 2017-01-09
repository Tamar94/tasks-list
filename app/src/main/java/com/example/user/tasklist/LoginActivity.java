package com.example.user.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tasklist.Utils.KeyboardManager;
import com.example.user.tasklist.Utils.UsersManager;


public class LoginActivity extends AppCompatActivity {
    private TextView inputUsername, inputPassword;
    private UsersManager usersManager;
    private Intent i;
    private Button btn;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check which user is login
        usersManager = new UsersManager(this);
        String userLogin = usersManager.whichUserLogin();
        //if user already logged in - go to task activity
        if (userLogin!=null) {
            i = new Intent(this, TaskActivity.class);
            this.startActivity(i);
        }
        setContentView(R.layout.login);
        inputUsername = (TextView)findViewById(R.id.inputUsername);
        inputPassword = (TextView)findViewById(R.id.inputPassword);
    }

    @Override
    protected void onStart() {
        super.onStart();
        i = getIntent();
    }

    public void login(View v)
    {
        KeyboardManager.hideKeyboard(this);
        //get username & password
        String username=inputUsername.getText().toString(), password=inputPassword.getText().toString(),
                msg = "User doesn't exist"; //default msg
        if (!username.isEmpty()) //not empty
        {
            //get user password-if user doesn't exist-get null
            String correctPassword = usersManager.getPassword(username);
            //user exists
            if (correctPassword!=null){
                //password is correct
                if (correctPassword.equals(password)){
                    //save user as login - format: <String name="UserLogin">username</String>
                    usersManager.login(username);
                    msg="Welcome "+username+"!";
                    //go to task list
                    i = new Intent(this, TaskActivity.class);
                    this.startActivity(i);
                    }
                else
                    msg="Password is wrong";
            }
        }
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void register(View v)
    {
        KeyboardManager.hideKeyboard(this);
        //get username & password
        String username=inputUsername.getText().toString(), password=inputPassword.getText().toString(),
                msg = "User already exists"; //default msg
        //if user doesn't exist - getPassword will return null
        if(usersManager.getPassword(username)==null)
        {
            //username is not empty & valid
            if (!username.isEmpty())if (checkUsername(username)){
                //password is not empty & valid - can register
                if (!password.isEmpty())if(checkPassword(password))
                {
                    //add user to users file - format: <String name=username>password</String>
                    usersManager.addUser(username,password);
                    //save user as login - format: <String name="UserLogin">username</String>
                    usersManager.login(username);
                    msg="Welcome "+username+"!";
                    //go to task list
                    i = new Intent(this, TaskActivity.class);
                    this.startActivity(i);
                }
                else //password is empty or not valid
                    msg="Password is not valid";
            }
            else //username is empty or not valid
                msg="Username is not valid";
        }
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void showUsernameInfo(View v)
    {
        String msg = "Username must begin with a letter, " +
                    "and can only contains letters and numbers.";
        btn = (Button) findViewById(R.id.usernameInfoBtn);
        showPopup(msg);
    }

    public void showPasswordInfo(View v)
    {
        String msg = "password must contains capital and " +
                    "regular letters,numbers and special characters." +
                    " Length between 6-12 characters.";
        btn = (Button) findViewById(R.id.passwordInfoBtn);
        showPopup(msg);
    }

    public void dismissPopup(View v)
    {
        popupWindow.dismiss();
    }

    private void showPopup(String msg)
    {
        View popupView = LayoutInflater.from(this).inflate(R.layout.info, null);
        ((TextView)popupView.findViewById(R.id.infoText)).setText(msg);
        popupWindow = new PopupWindow(popupView,
                                      ViewGroup.LayoutParams.WRAP_CONTENT,
                                      ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(btn);
    }

    private boolean checkUsername(String user)
    {
        //username must stars at letter and can only contains letters & numbers
        return user.matches("^[a-zA-z]+([a-zA-z0-9])*$");
    }

    private boolean checkPassword(String password)
    {
        //password must contains capital & regular letter,numbers and special characters
        //length between 6-12 characters
        return  password.matches("^(?=.*?[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[#?!@$%^&*-]).{6,12}$");
    }

}
