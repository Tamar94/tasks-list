package com.example.user.tasklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.tasklist.Listeners.CheckBoxListener;
import com.example.user.tasklist.Listeners.TaskTextListener;
import com.example.user.tasklist.R;
import com.example.user.tasklist.Utils.TasksManager;


public class TasksAdapter extends BaseAdapter {
    private final Context CONTEXT;
    private final Object [] DATA;
    private final TasksManager TM;
    private  final ListView LIST_VIEW;


    public TasksAdapter(Object[] data, Context context, TasksManager tm, ListView listView){
        this.DATA=data;
        this.CONTEXT=context;
        this.TM = tm;
        this.LIST_VIEW = listView;
    }

    @Override
    public int getCount() {
        return DATA.length;
    }

    @Override
    public Object getItem(int i) {
        return DATA[i];
    }

    @Override
    public View getView(int i, View recycleView, ViewGroup parent) {
        final String TASK_TEXT = (String)DATA[i];

        if(recycleView==null)
            recycleView = LayoutInflater.from(CONTEXT).inflate(R.layout.task,null);
        TextView task = (TextView)recycleView.findViewById(R.id.task);
        //show relevant text in reused view
        task.setText(TASK_TEXT);
        //set long click listener for the task text
        task.setOnLongClickListener(new TaskTextListener(CONTEXT,TM,LIST_VIEW));

        CheckBox cb = (CheckBox)recycleView.findViewById(R.id.checkbox);
        cb.setOnClickListener(new CheckBoxListener(CONTEXT,TM,LIST_VIEW,TASK_TEXT));

        return recycleView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
