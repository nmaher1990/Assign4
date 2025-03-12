package com.example.sdaassign4_2022;


import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sdaassign4_2022.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    private static final String BORROWER_NAME_KEY = "BORROWER_NAME_KEY";
    private static final String EMAIL_ADDRESS_KEY = "EMAIL_ADDRESS_KEY";
    private static final String BORROWER_ID_KEY = "BORROWER_ID_KEY";

    private EditText editBorrowerName;
    private EditText editEmailAddress;
    private EditText editBorrowerID;

    private SharedPreferences prefs;

    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        editBorrowerName = view.findViewById(R.id.userName);
        editEmailAddress = view.findViewById(R.id.email);
        editBorrowerID = view.findViewById(R.id.borrowerID);
        Button buttonSave = view.findViewById(R.id.button);
        Button buttonClear = view.findViewById(R.id.buttonClear);

        prefs = requireActivity().getSharedPreferences("UserPreferences", MODE_PRIVATE);

        editBorrowerName.setText(String.valueOf(prefs.getString(BORROWER_NAME_KEY, "")));
        editEmailAddress.setText(prefs.getString(EMAIL_ADDRESS_KEY, ""));
        editBorrowerID.setText(prefs.getString(BORROWER_ID_KEY, ""));

        buttonSave.setOnClickListener(v -> saveUserData());
        buttonClear.setOnClickListener(v -> clearUserData());

        return view;
    }

    private void saveUserData() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BORROWER_NAME_KEY, editBorrowerName.getText().toString());
        editor.putString(EMAIL_ADDRESS_KEY, editEmailAddress.getText().toString());
        editor.putString(BORROWER_ID_KEY, editBorrowerID.getText().toString());
        editor.apply();

        Toast.makeText(requireContext(), "Settings Saved", Toast.LENGTH_SHORT).show();

    }

    private void clearUserData() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BORROWER_NAME_KEY, "");
        editor.putString(EMAIL_ADDRESS_KEY, "");
        editor.putString(BORROWER_ID_KEY, "");
        editor.apply();

        editBorrowerName.setText("");
        editEmailAddress.setText("");
        editBorrowerID.setText("");

        Toast.makeText(requireContext(), "Settings Cleared", Toast.LENGTH_SHORT).show();
    }

    private SharedPreferences getPreferences(int modePrivate) {
        return null;
    }

}
