package com.example.finalstep2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.finalstep2.R;
import com.example.finalstep2.utils.SharedPreferencesLanguage;
import com.google.android.material.button.MaterialButton;

public class DialogLanguage extends DialogFragment {

    private RadioButton radioIR;
    private RadioButton radioEn;
    private MaterialButton btnOk;
    private OnChangeLanguage onChangeLanguage;
    private String language = null;
    private SharedPreferencesLanguage preferencesLanguage;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnChangeLanguage)
            onChangeLanguage = (OnChangeLanguage) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesLanguage = new SharedPreferencesLanguage(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_language, null);
        radioEn = view.findViewById(R.id.radioEn);
        radioIR = view.findViewById(R.id.radioIR);
        btnOk = view.findViewById(R.id.btnOK);
        getLanguage();
        radioIR.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                language = "fa";
                radioEn.setChecked(false);
            }
            preferencesLanguage.saveLn(language);
        });
        radioEn.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                language = "en";
                radioIR.setChecked(false);
            }
            preferencesLanguage.saveLn(language);
        });

        btnOk.setOnClickListener(view1 -> {
            if (language != null) {
                onChangeLanguage.onChange(language);
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();

    }

    public interface OnChangeLanguage {
        void onChange(String language);
    }

    private void getLanguage() {
        if (preferencesLanguage.getLn().equals("en")) {
            language = "en";
            radioEn.setChecked(true);
        } else if (preferencesLanguage.getLn().equals("fa")) {
            language = "fa";
            radioIR.setChecked(true);
        }
    }

}
