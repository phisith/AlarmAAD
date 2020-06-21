package com.example.alarmaad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePicker extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //to get the hour values that click on the timepciker
        int min = calendar.get(Calendar.MINUTE); //to get the minute values that click on the timepicker

        //return all the values to the timepickerdialog
        return new TimePickerDialog(getActivity(), R.style.ThemeOverlay_AppCompat_DayNight_ActionBar,(TimePickerDialog.OnTimeSetListener) getActivity(), hour, min, DateFormat. is24HourFormat(getActivity()));
    }
}
