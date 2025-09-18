package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void editCity(City city);
    }
    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityFragment.AddCityDialogListener) {
            listener = (EditCityFragment.EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        System.out.println("Hello");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_current_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_current_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    listener.editCity(new City(cityName, provinceName));
                })
                .create();
    }


}
