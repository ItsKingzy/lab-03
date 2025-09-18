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
        void editCity(City city, int position);
    }

    private EditCityDialogListener listener;

    // Implemented based on the lab slide example (page 10)
    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable("City", city);      // No longer need to cast to Serializable
        args.putInt("Position", position);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // From slides
        if (context instanceof EditCityFragment.EditCityDialogListener) {
            listener = (EditCityFragment.EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // fragment_add_city.xml would have been the same as fragment_edit_city.xml
        // Therefore fragment_edit_city.xml is redundant
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        /*
        - Establish Bundle args
        - Fetch city (includes city and province data)
        - Fetch the position on screen
         */
        Bundle args = getArguments();
        City city = (City) args.getSerializable("City");
        int position = args.getInt("Position");

        // Reusing fragment_add_city.xml for simplicity
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Sets the text for the dialog popup
        editCityName.setText(city.getName());               // Getter name of city
        editProvinceName.setText(city.getProvince());       // Getter name of province

        /*
        Copy paste from the lab slides... not 100% sure what this does, just know it works
        for the dialog popup. Subtle changes were made in order for it to function with the
        edit city functionality
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City updatedCity = new City(cityName, provinceName);
                    listener.editCity(updatedCity, position);
                })
                .create();
    }
}

