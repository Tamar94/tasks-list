package com.example.user.tasklist.Listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import com.example.user.tasklist.Utils.TasksManager;

public class CheckBoxListener implements View.OnClickListener {
    private final Context CONTEXT;
    private final TasksManager TM;
    private final ListView LIST_VIEW;
    private final String TASK;

    public CheckBoxListener(Context c, TasksManager tm, ListView listView, String task)
    {
        this.CONTEXT = c;
        this.TM = tm;
        this.LIST_VIEW = listView;
        this.TASK = task;
    }

    @Override
    public void onClick(View v) {
        final CheckBox CB = (CheckBox)v;

        AlertDialog.Builder dialog = new AlertDialog.Builder(CONTEXT)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //unchecked checkbox
                        CB.setChecked(false);}
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //change task from tasks/completed to the second set
                        TM.changeTaskSet(TASK);
                        //reload currently tasks view list
                        TM.reloadTasksListView(CONTEXT,LIST_VIEW);}
                });
        //check in which activity the user - task/completed, and set title respectively
        if (TM.getName().equals("tasks")) dialog.setTitle("Task completed?");
        else dialog.setTitle("Task not completed?");
        dialog.show();
    }
}

