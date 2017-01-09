package com.example.user.tasklist.Listeners;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.user.tasklist.Utils.KeyboardManager;
import com.example.user.tasklist.Utils.TasksManager;


public class TaskTextListener implements View.OnLongClickListener  {
    private final Context CONTEXT;
    private final TasksManager TM;
    private final ListView LIST_VIEW;

    public TaskTextListener(Context context, TasksManager tm, ListView listView) {
        CONTEXT = context;
        TM = tm;
        LIST_VIEW = listView;
    }

    @Override
    public boolean onLongClick(View v) {
        final String TASK = ((TextView)v).getText().toString();

        new  AlertDialog.Builder(CONTEXT)
                .setTitle(TASK)
                .setPositiveButton("Edit", new EditListener(TASK))
                .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //remove task & reload tasks list view
                        TM.removeTask(TASK);
                        TM.reloadTasksListView(CONTEXT,LIST_VIEW);
                    }
                })
                .setNeutralButton("Cancel",null)
                .show();
        return false;
    }

    private class EditListener implements DialogInterface.OnClickListener
    {
        private final String TASK;

        private EditListener(String task) {
            TASK = task;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            final EditText editTask = new EditText(CONTEXT);
            editTask.setText(TASK);

           new AlertDialog.Builder(CONTEXT)
                    .setCancelable(false)
                    .setView(editTask)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          //get user edit task
                          String taskAfterEdit = editTask.getText().toString();
                          //check if after edit task not exist - if not, save the edit task
                          if (TM.saveTask(taskAfterEdit,CONTEXT)) {
                              //remove original task
                              TM.removeTask(TASK);
                              //reload tasks list view
                              TM.reloadTasksListView(CONTEXT,LIST_VIEW);
                          }
                          KeyboardManager.hideKeyboard((Activity) CONTEXT);
                        }
                    })
                   .setNeutralButton("Remind", new ReminderListener(CONTEXT))
                   .setNegativeButton("Cancel",null)
                   .show();
        }
    }
}
