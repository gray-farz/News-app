package com.example.finalstep2.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.finalstep2.R;
import com.example.finalstep2.utils.DateUtils;
import com.google.android.material.button.MaterialButton;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

public class FilterDialogFa extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private OnClickFilter onClickFilter;
    public static int CAT_SELECT;

    private MaterialButton btnFrom;
    private MaterialButton btnTo;
    private MaterialButton btnFilter;
    private MaterialButton btnCancel;
    private RadioButton radioMobile;
    private RadioButton radioIntelligence;

    private String catFilter = null;
    private int request = -1;
    private int FROM_REQUEST = 123;
    private int TO_REQUEST = 321;
    private boolean isCheckDate = false;
    private String dateFromFilter = null;
    private String dateToFilter = null;
    private String dateFrom = null;
    private String dateTo = null;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickFilter) {
            onClickFilter = (OnClickFilter) context;
        }
        CAT_SELECT = getArguments().getInt("au");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter_fa, null);

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
        } else if (CAT_SELECT == 1) {
            catFilter = "cat&name=1";
            radioMobile.setChecked(true);
        } else {
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

        btnTo.setOnClickListener(view1 -> {
            getDate();
            request = TO_REQUEST;
        });

        btnFilter.setOnClickListener(view1 -> {
            if (catFilter != null) {
                onClickFilter.onCat(catFilter);
                dismiss();
            } else if (isCheckDate) {
                onClickFilter.onFilterDate(generateDateFilter());
                dismiss();
            }
        });

        btnCancel.setOnClickListener(view1 -> {
            onClickFilter.onCancelFilter("");
            CAT_SELECT =0;
            catFilter =null;
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }

    public static FilterDialogFa newInstance(int cat) {
        Bundle args = new Bundle();
        args.putInt("au", cat);
        FilterDialogFa fragment = new FilterDialogFa();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (request == FROM_REQUEST) {
            monthOfYear++;
            btnFrom.setText(setDateText(year, monthOfYear, dayOfMonth));
            dateFrom = DateUtils.GetDate(year, monthOfYear, dayOfMonth);
            dateFromFilter = setDateText(year, monthOfYear, dayOfMonth);
        } else if (request == TO_REQUEST) {
            monthOfYear++;
            btnTo.setText(setDateText(year, monthOfYear, dayOfMonth));
            dateTo = DateUtils.GetDate(year, monthOfYear, dayOfMonth);
            dateToFilter = setDateText(year, monthOfYear, dayOfMonth);
        }

        if (dateFrom != null && dateTo != null) {
            if (DateUtils.CheckEqul(dateFrom, dateTo)) {
                isCheckDate = true;
                catFilter = null;
                CAT_SELECT = 0;
            } else {
                isCheckDate = false;
            }
        }
    }

    public interface OnClickFilter {
        void onCat(String filter);
        void onFilterDate(String filter);
        void onCancelFilter(String filter);
    }
    private void getDate() {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this::onDateSet,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
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
