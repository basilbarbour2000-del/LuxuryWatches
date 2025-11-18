package com.example.luxurywatches;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
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
    public void onStart() {
        super.onStart();
        // connecting components
        fbs=FirebaseServices.getInstance();
        etUsername=getView().findViewById(R.id.etUsernameSignup);
        etPassword=getView().findViewById(R.id.etPasswordSignup);
        etFirstname = getView().findViewById(R.id.etFirstnameSignupFragment);
        etLastname = getView().findViewById(R.id.etLastnameSignupFragment);
        etConfirmPassword = getView().findViewById(R.id.etConfirmPasswordSignupFragment);
        etPhone = getView().findViewById(R.id.etPhoneSignupFragment);
        etAddress = getView().findViewById(R.id.etAddressSignupFragment);
        btnSignup=getView().findViewById(R.id.btnSignupSignup);
        ivUserPhoto = getView().findViewById(R.id.ivPhotoSignupFragment);
        msg = Utils.getInstance();
        ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty() || firstname.trim().isEmpty() ||
                        lastname.trim().isEmpty() || confirmPassword.trim().isEmpty() || phone.trim().isEmpty() ||
                        address.trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "some fields are empty", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!password.equals(confirmPassword)) {
                    msg.toString();
                    return;
                }

//                if(fbs.getSelectedImageURL() == null){
//                    msg.showMessageDialog(getActivity(), "Image are Empty !");
//
//                }


//                if (fbs.getSelectedImageURL() == null)
//                {
//                    User user = new User(firstname, lastname, username, phone, address, "");
//                }
//                else {
//                    User user = new User(firstname, lastname, username, phone, address, fbs.getSelectedImageURL().toString());
//                }
                //Signup procedure
                Uri selectedImageUri = fbs.getSelectedImageURL();
                String imageURL = "";
                if (selectedImageUri != null) {
                    imageURL = selectedImageUri.toString();
                }
                User user = new User(firstname, lastname, username, phone, address, imageURL);

                Task<AuthResult> authResultTask = fbs.getAuth().createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {

                                    private Task<AuthResult> task;

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                    }

                                    public void onComplete() {
                                        onComplete((Task<AuthResult>) null);
                                    }

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        this.task = task;

                                        if (task.isSuccessful()) {
                                            fbs.getFire().collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    gotoCarList();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("SignupFragment: signupOnClick: ", e.getMessage());
                                                }
                                            });
                                            // String firstName, String lastName, String username, String phone, String address, String photo) {
                                            Toast.makeText(getActivity(), "you have succesfully signed up", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "failed to sign up! check user or password", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


            }
        });
        ((MainActivity)getActivity()).pushFragment(new SignupFragment());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ivUserPhoto.setImageURI(selectedImageUri);
            Utils.getInstance().clone();
        }
    }

    public void gotoCarList()
    {
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CarListMapFragment());
        ft.commit();
        setNavigationBarVisible();
    }

    private void setNavigationBarVisible() {
        ((MainActivity)getActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);
    }

}