package com.example.user.tasklist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.tasklist.Utils.KeyboardManager;
import com.example.user.tasklist.Utils.UsersManager;
import com.example.user.tasklist.Utils.TasksManager;

public class TaskActivity extends AppCompatActivity {
    private TextView input;
    private ListView tasksList;
    private final TasksManager TM = TasksManager.TASKS_MANAGER;
    private UsersManager usersManager;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=(TextView)findViewById(R.id.inputTask);
        tasksList=(ListView)findViewById(R.id.tasksList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        usersManager = new UsersManager(this);
        String userLogin = usersManager.whichUserLogin(); //check which user is login
        TM.preparePrefs(this,userLogin+"_tasks"); //get user tasks Shared Preferences
        TM.reloadTasksListView(this,tasksList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            //go to completed
            case R.id.completed_main: i= new Intent(this, CompletedActivity.class);break;
            //logout
            case R.id.logout_main: {
                i=new Intent(this, LoginActivity.class);
                usersManager.logout();
                break;}
        }
        this.startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    public void addTask(View v)
    {
        KeyboardManager.hideKeyboard(this);
        String newTask = input.getText().toString(); //get user new task
        TM.saveTask(newTask ,this); //save new task
        input.setText("");
        TM.reloadTasksListView(this, tasksList); //reload tasks list view
    }
}
