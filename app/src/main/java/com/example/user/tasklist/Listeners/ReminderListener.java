package com.example.user.tasklist.Listeners;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class ReminderListener implements DialogInterface.OnClickListener{
    private final Context CONTEXT;
    private final Calendar CALENDER;

    public ReminderListener(Context c)
    {
        CONTEXT = c;
        CALENDER = Calendar.getInstance();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        final DialogInterface.OnClickListener NULL_LISTENER = null;
        //create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(CONTEXT
                , new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //put user date in the calender
                        CALENDER.set(Calendar.YEAR, year);
                        CALENDER.set(Calendar.MONTH, month);
                        CALENDER.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }}
                //set default day
                ,CALENDER.get(Calendar.YEAR),CALENDER.get(Calendar.HOUR_OF_DAY)
                ,CALENDER.get(Calendar.MINUTE));

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Save",
                  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //let the user choose time
                        TimePickerDialog timePickerDialog = new TimePickerDialog(CONTEXT
                           ,new TimePickerDialog.OnTimeSetListener() {
                              public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //put user time in the calender
                                CALENDER.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                CALENDER.set(Calendar.MINUTE, minute);
                            }}
                            //set default time
                          ,CALENDER.get(Calendar.HOUR_OF_DAY), CALENDER.get(Calendar.MINUTE), true);

                  timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "save",
                          new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                 //send calender to reminderManager
                                  //storage date&time
                              }
                          });
                  timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",NULL_LISTENER);
                  timePickerDialog.show();
                    }
                });
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", NULL_LISTENER);
        datePickerDialog.show();
    }
}

