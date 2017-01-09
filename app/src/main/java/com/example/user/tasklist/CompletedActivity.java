package com.example.user.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.user.tasklist.Utils.UsersManager;
import com.example.user.tasklist.Utils.TasksManager;

public class CompletedActivity extends AppCompatActivity {
    private UsersManager usersManager;
    private ListView completedList;
    private final TasksManager TM = TasksManager.COMPLETED_TASKS_MANAGER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed);
        completedList=(ListView)findViewById(R.id.completedList);

    }
    @Override
    protected void onStart() {
        super.onStart();
        usersManager = new UsersManager(this);
        String userLogin = usersManager.whichUserLogin(); //check which user is login
        TM.preparePrefs(this,userLogin+"_tasks"); //get user tasks Shared Preferences
        TM.reloadTasksListView(this, completedList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.completed_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i=null;
        switch (item.getItemId())
        {
            //go back to task list
            case R.id.back_completed: i= new Intent(this, TaskActivity.class);break;
            //logout
            case R.id.logout_completed: {
                i=new Intent(this, LoginActivity.class);
                usersManager.logout();
                break;}
        }
        this.startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}


