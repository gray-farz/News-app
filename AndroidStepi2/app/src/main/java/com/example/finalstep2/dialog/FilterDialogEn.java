package com.example.finalstep2.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.finalstep2.R;
import com.example.finalstep2.utils.DateUtils;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class FilterDialogEn extends DialogFragment {

    private MaterialButton btnFrom;
    private MaterialButton btnTo;
    private MaterialButton btnFilter;
    private MaterialButton btnCancel;
    private RadioButton radioMobile;
    private RadioButton radioIntelligence;
    private OnClickFilterEn clickFilterEn;
    private String catFilter = null;
    public static int CAT_SELECT;
    private int request = -1;
    private int FROM_REQUEST = 223;
    private int TO_REQUEST = 421;
    private String dateFrom = null;
    private String dateTo = null;
    private String dateFromFilter = null;
    private String dateToFilter = null;
    private boolean isCheckDate = false;
    private int mYear, mMonth, mDay;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickFilterEn) {
            clickFilterEn = (OnClickFilterEn) context;
        }
        CAT_SELECT = getArguments().getInt("au");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_en, null);

        btnFrom = view.findViewById(R.id.btnFrom);
        btnTo = view.findViewById(R.id.btnTo);
        btnFilter = view.findViewById(R.id.btnFilter);
        radioIntelligence = view.findViewById(R.id.radioIntelligence);
        radioMobile = view.findViewById(R.id.radioMobile);
        btnCancel = view.findViewById(R.id.btnCancel);

        if (CAT_SELECT == 0) {
            catFilter = null;
            radioMobile.setChecked(false);
            radioIntelligence.setChecked(false);
        }
        else if (CAT_SELECT == 1) {
            catFilter = "cat&name=1";
            radioMobile.setChecked(true);
        }
        else {
            catFilter = "cat&name=2";
            radioIntelligence.setChecked(true);
        }

        radioIntelligence.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                catFilter = "cat&name=2";
                radioMobile.setChecked(false);
                CAT_SELECT = 2;
            }
        });

        radioMobile.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                catFilter = "cat&name=1";
                radioIntelligence.setChecked(false);
                CAT_SELECT = 1;
            }
        });

        btnFrom.setOnClickListener(view1 -> {
            getDate();
            request = FROM_REQUEST;
        });

        btnTo.setOnClickListener(view1 ->{
            getDate();
            request = TO_REQUEST;
        });

        btnFilter.setOnClickListener(view1 -> {
            if (catFilter != null)
            {
                clickFilterEn.onCatEn(catFilter);
                dismiss();
            }
            else if (isCheckDate)
            {
                clickFilterEn.onFilterDateEn(generateDateFilter());
                dismiss();
            }
        });

        btnCancel.setOnClickListener(view1 -> {
            clickFilterEn.onCancelFilterEn("");
            CAT_SELECT = 0;
            catFilter = null;
            dismiss();
        });

        builder.setView(view);
        return builder.create();

    }

    public static FilterDialogEn newInstance(int cat) {
        Bundle args = new Bundle();
        args.putInt("au", cat);
        FilterDialogEn fragment = new FilterDialogEn();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnClickFilterEn {
        void onCatEn(String filter);
        void onFilterDateEn(String filter);
        void onCancelFilterEn(String filter);
    }

    private void getDate() {

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year, int month, int dayOfMonth) {
                if (request == FROM_REQUEST)
                {

                    month++;
                    btnFrom.setText(setDateText(year, month, dayOfMonth));
                    dateFrom = DateUtils.GetDate(year, month, dayOfMonth);
                    dateFromFilter = setDateText(year, month, dayOfMonth);
                } else if (request == TO_REQUEST) {
                    month++;
                    btnTo.setText(setDateText(year, month, dayOfMonth));
                    dateTo = DateUtils.GetDate(year, month, dayOfMonth);
                    dateToFilter = setDateText(year, month, dayOfMonth);
                }
                if (dateFrom != null && dateTo != null)
                {
                    if (DateUtils.CheckEqul(dateFrom, dateTo)) {
                        isCheckDate = true;
                        catFilter = null;
                        CAT_SELECT = 0;
                    } else {
                        isCheckDate = false;
                    }
                }
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private String setDateText(int years, int month, int day) {
        String convert = String.valueOf(years) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        return convert;
    }

    private String generateDateFilter() {
        String filterDate = "";
        filterDate = "date&befor=" + dateFromFilter + "&after=" + dateToFilter;
        return filterDate;
    }

}
