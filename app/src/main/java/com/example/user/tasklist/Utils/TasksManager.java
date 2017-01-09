package com.example.user.tasklist.Utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Toast;
import com.example.user.tasklist.adapters.TasksAdapter;
import java.util.HashSet;
import static android.content.Context.MODE_PRIVATE;

public enum TasksManager {
    TASKS_MANAGER("tasks"), COMPLETED_TASKS_MANAGER("completed");
    private final String name;
    private SharedPreferences prefs;
    TasksManager(String name){
        this.name=name;
    }

    //must call this method before using other methods
    public void preparePrefs (Context c, String fileName)
    {
       prefs = c.getSharedPreferences(fileName, MODE_PRIVATE);
    }

    public String getName()
    {return this.name;}

    public HashSet<String> getTasks() {
        HashSet<String> tasks = new HashSet<>();
        //get tasks/completed tasks from user tasks file
        HashSet<String> temp = (HashSet<String>) prefs.getStringSet(this.name, null);
        if (temp != null) tasks.addAll(temp);
        //if user don't have tasks/completed tasks - return empty HashSet
        return tasks;
    }

    public void updateTasksSet(HashSet<String> tasks)
    {
        prefs.edit().putStringSet(this.name,tasks).commit();
    }

    public void changeTaskSet(String task)
    {
        HashSet<String> temp = this.getTasks();
        temp.remove(task); //remove task from currently set(tasks/completed tasks)
        this.updateTasksSet(temp); //update the set without the task
        //check to which set pass the task(the other set - not this)
        if (this.getName().equals("tasks")) {
            COMPLETED_TASKS_MANAGER.prefs = this.prefs;
            temp = COMPLETED_TASKS_MANAGER.getTasks();
            temp.add(task);
            COMPLETED_TASKS_MANAGER.updateTasksSet(temp);
            }
        else {
            TASKS_MANAGER.prefs = this.prefs;
            temp = TASKS_MANAGER.getTasks();
            temp.add(task);
            TASKS_MANAGER.updateTasksSet(temp);
            }
    }

    public boolean saveTask(String newTask,Context c)
    {
        HashSet<String> tasks = this.getTasks();
        //if not empty and not duplicate-add locally
        if(!newTask.isEmpty()) if (tasks.add(newTask)){
            this.updateTasksSet(tasks);//put new task in user tasks file
            Toast.makeText(c,"Task Saved",Toast.LENGTH_SHORT).show();
            return true;}
        else
            Toast.makeText(c,"Task is already exist",Toast.LENGTH_SHORT).show();
        return false;
    }

    public void removeTask(String task)
    {
        HashSet<String> tasks = this.getTasks();
        tasks.remove(task);
        this.updateTasksSet(tasks);
    }

    public void reloadTasksListView(Context c,ListView tasksList)
    {
        tasksList.setAdapter(new TasksAdapter(this.getTasks().toArray(),c,this,tasksList));
    }
}
