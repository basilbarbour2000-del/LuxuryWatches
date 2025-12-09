package com.example.luxurywatches;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

public class SignupFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 123;

    private EditText etUsername, etPassword, etConfirmPassword,
            etFirstname, etLastname, etPhone, etAddress;

    private ImageView ivUserPhoto;
    private Button btnSignup;

    private FirebaseServices fbs;

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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Firebase
        fbs = FirebaseServices.getInstance();

        // Connect components
        etUsername = getView().findViewById(R.id.etUsernameSignup);
        etPassword = getView().findViewById(R.id.etPasswordSignup);
        etConfirmPassword = getView().findViewById(R.id.etConfirmPasswordSignupFragment);
        etFirstname = getView().findViewById(R.id.etFirstnameSignupFragment);
        etLastname = getView().findViewById(R.id.etLastnameSignupFragment);
        etPhone = getView().findViewById(R.id.etPhoneSignupFragment);
        etAddress = getView().findViewById(R.id.etAddressSignupFragment);
        ivUserPhoto = getView().findViewById(R.id.ivPhotoSignupFragment);
        btnSignup = getView().findViewById(R.id.btnSignupSignup);

        // Choose image
        ivUserPhoto.setOnClickListener(v -> openGallery());

        // Signup click
        btnSignup.setOnClickListener(view -> validateAndSignup());
    }


    private void validateAndSignup() {
        // Read data
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String firstname = etFirstname.getText().toString().trim();
        String lastname = etLastname.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // Empty fields
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || address.isEmpty()) {

            Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Password mismatch
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected image
        Uri selectedImageUri = fbs.getSelectedImageURL();
        String imageURL = (selectedImageUri != null) ? selectedImageUri.toString() : "";

        // Create user object
        User user = new User(firstname, lastname, username, phone, address, imageURL);

        // Create account in Firebase
        fbs.getAuth().createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener(authResult -> gotoMainPage())
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Signup failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }


    // Open gallery
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    // Receive selected image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ivUserPhoto.setImageURI(selectedImageUri);

            // Save in FirebaseServices
            fbs.setSelectedImageURL(selectedImageUri);
        }
    }

    // Go to next fragment
    public void gotoMainPage() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new AllFragment());
        ft.commit();
    }
}
