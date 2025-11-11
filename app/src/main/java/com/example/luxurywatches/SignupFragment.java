package com.example.luxurywatches;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.installations.Utils;

public class SignupFragment extends Fragment {
    private static final int GALLERY_REQUEST_CODE = 123;
    private EditText etUsername,etPassword, etConfirmPassword,
            etFirstname, etLastname, etPhone, etAddress;
    ImageView ivUserPhoto;
    private Button btnSignup;
    private FirebaseServices fbs;
    private Utils msg;


    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

}